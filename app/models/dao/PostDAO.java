package models.dao;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Category;
import models.Post;

import org.joda.time.DateTime;

import play.utils.cache.CachedFinder;
import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

@Singleton
public class PostDAO extends CachedDAO<Long, Post> {

	private static final int TOP_10 = 10;

	public InterimCache<Post> cache = new InterimCache<Post>(Post.class,
			3600);
	
	private CachedFinder<Long, Post> find;

	@Inject
	public PostDAO(OwnerCacheCleaner<Long, Post> cacheCleaner) {
		super(Long.class, Post.class);
		addListener(new TimestampListener<Long, Post>());
		addListener(cacheCleaner);
		find = cacheFind();
	}

	/**
	 * Return a page of posts
	 * 
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 */
	public Page<Post> page(int page, int pageSize) {
		return find.page(page, pageSize, "createdOn desc");
	}

	public Page<Post> pageInCategory(Category category, int page,
			int pageSize) {
		return find.where().eq("category", category).orderBy("createdOn desc")
				.findPagingList(pageSize).getPage(page);
	}

	public Page<Post> topDayPage() {
		return cache.page(".topDay", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				DateTime now = new DateTime();
				DateTime yesterday = now.minusDays(1);
				return find.where().gt("createdOn", yesterday.toDate())
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public Page<Post> topWeekPage() {
		return cache.page(".topWeek", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				DateTime now = new DateTime();
				DateTime lastWeek = now.minusDays(7);
				return find.where().gt("createdOn", lastWeek.toDate())
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public Page<Post> topAllPage() {
		return cache.page(".topAll", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				return find.where().orderBy("rating desc")
						.findPagingList(TOP_10).getPage(0);
			}
		});
	}

}
