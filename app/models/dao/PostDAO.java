package models.dao;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Category;
import models.ContentStatus;
import models.Post;

import org.joda.time.DateTime;

import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Page;

@Singleton
public class PostDAO extends CachedDAO<Long, Post> {

	private static final int TOP_10 = 10;

	protected InterimCache<Post> cache = new InterimCache<Post>(Post.class,
			3600);
	
	@Inject
	public PostDAO(OwnerCacheCleaner<Long, Post> cacheCleaner) {
		super(Long.class, Post.class);
		addListener(new TimestampListener<Long, Post>());
		addListener(cacheCleaner);
	}

	public Page<Post> page(int page, int pageSize) {
		String cacheKey = "page=" + page;
		Expression expr = Expr.ne("status", ContentStatus.REMOVED);
		return find.page(page, pageSize, "createdOn desc", cacheKey, expr);
	}

	public Page<Post> pageInCategory(Category category, int page,
			int pageSize) {
		String cacheKey = "category=" + category + ", page=" + page;
		Expression expr = Expr.and(Expr.eq("category", category),
				Expr.ne("status", ContentStatus.REMOVED));
		return find.page(page, pageSize, "createdOn desc", cacheKey, expr);
	}

	public Page<Post> topDayPage() {
		return cache.page(".topDay", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				DateTime now = new DateTime();
				DateTime yesterday = now.minusDays(1);
				return find.where().gt("createdOn", yesterday.toDate())
						.ne("status", ContentStatus.REMOVED)
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
						.ne("status", ContentStatus.REMOVED)
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public Page<Post> topAllPage() {
		return cache.page(".topAll", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				return find.where().ne("status", ContentStatus.REMOVED)
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public Page<Post> getPostsCreatedBy(List<String> usernames, int page,
			int pageSize) {
		return find.where().in("created_by", usernames)
				.orderBy("createdOn desc").findPagingList(pageSize)
				.getPage(page);
	}

}
