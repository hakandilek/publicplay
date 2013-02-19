package models.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import javax.inject.Singleton;
import javax.inject.Inject;

import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;
import play.db.ebean.Model.Finder;
import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;

@Singleton
public class PostRatingDAO extends CachedDAO<PostRatingPK, PostRating> {

	protected static InterimCache<Set<Long>> votedPostKeyCache = new InterimCache<Set<Long>>("VotedPostKeyCache", 600);//10 mins;

	protected static InterimCache<Set<Post>> upVotedPostCache = new InterimCache<Set<Post>>("UpVotedPostCache", 600);//10 mins;

	protected Finder<PostRatingPK, PostRating> find = new Finder<PostRatingPK, PostRating>(
			PostRatingPK.class, PostRating.class);

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

	public PostRating get(String userKey, Long postKey) {
		PostRatingPK key = new PostRatingPK(userKey, postKey);
		return find.byId(key);
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
	
	public Set<Post> getUpVotedPosts(final User u) {
		String key = u.getKey();
		final Set<Post> set = upVotedPostCache.get(".+" + key, new Callable<Set<Post>>() {
			public Set<Post> call() throws Exception {
				Set<Post> s = new TreeSet<Post>();
				Set<Long> upVotedPostKeys = getUpVotedPostKeys(u);
				for (Long key : upVotedPostKeys) {
					Post post = postDAO.get(key);
					if (post != null) s.add(post);
				}
				return s;
			}
		});
		return set;
	}
	
	public void resetVotedPostKeyCache(User u) {
		String key = u.getKey();
		votedPostKeyCache.set(".+" + key, null);
		votedPostKeyCache.set(".-" + key, null);
		upVotedPostCache.set(".+" + key, null);
	}
	
}
