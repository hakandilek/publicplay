package models.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;
import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;
import play.utils.dao.*;

import com.avaje.ebean.Page;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@Singleton
public class PostRatingDAO extends CachedDAO<PostRatingPK, PostRating> {

	protected static final int PAGE_SIZE = 20;

	protected static InterimCache<Set<Long>> votedPostKeyCache = new InterimCache<Set<Long>>("VotedPostKeyCache", 600);//10 mins;

	protected static InterimCache<Page<Post>> upVotedPostCache = new InterimCache<Page<Post>>("UpVotedPostCache", 600);//10 mins;


	protected Multimap<String, String> postPages = HashMultimap.create();

	private PostDAO postDAO;

	@Inject
	public PostRatingDAO(PostDAO postDAO) {
		super(PostRatingPK.class, PostRating.class);
		this.postDAO = postDAO;
	}

	public PostRating get(User user, Post post) {
		PostRating rating = find.where().eq("user_key", user.getKey())
				.eq("post_key", post.getKey()).findUnique();
		return rating;
	}

	public List<PostRating> get(User user) {
		List<PostRating> ratings = find.where().eq("user_key", user.getKey())
				.findList();
		return ratings;
	}
	
	public Set<Long> getUpVotedPostKeys(final User u) {
		String key = u.getKey();
		final Set<Long> set = votedPostKeyCache.get(".+" + key, new Callable<Set<Long>>() {
			public Set<Long> call() throws Exception {
				Set<Long> s = new TreeSet<Long>();
				final List<PostRating> prList = get(u);
				for (PostRating pr : prList) {
					if (pr.getValue() > 0) s.add(pr.getKey().postKey);
				}
				return s;
			}
		});
		return set;
	}
	
	public Set<Long> getDownVotedPostKeys(final User u) {
		String key = u.getKey();
		final Set<Long> set = votedPostKeyCache.get(".-" + key, new Callable<Set<Long>>() {
			public Set<Long> call() throws Exception {
				Set<Long> s = new TreeSet<Long>();
				final List<PostRating> prList = get(u);
				for (PostRating pr : prList) {
					if (pr.getValue() < 0) s.add(pr.getKey().postKey);
				}
				return s;
			}
		});
		return set;
	}
	
	public Page<Post> getUpVotedPosts(final User u, final int page) {
		final String key = u.getKey();
		final String cacheKey = "." + key + ".pg." + page;
		final Page<Post> set = upVotedPostCache.get(cacheKey, new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				postPages.put(key, cacheKey);
				Page<PostRating> ratingPage = page(page, PAGE_SIZE, "created_on desc", "user_key", u.getKey());
				PageAdapter<PostRating, Post> postPage = new PostRatingPageAdapter(ratingPage, postDAO);
				return postPage;
			}
		});
		return set;
	}
	
	public void resetVotedPostKeyCache(User u) {
		String key = u.getKey();
		votedPostKeyCache.set(".+" + key, null);
		votedPostKeyCache.set(".-" + key, null);
		Collection<String> pageKeys = postPages.get(key);
		for (String pageKey : pageKeys) {
			upVotedPostCache.set(pageKey, null);
		}
		postPages.removeAll(key);
	}
	
}
