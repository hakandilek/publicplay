package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Comment;
import models.ContentStatus;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Page;

@Singleton
public class CommentDAO extends CachedDAO<Long, Comment> {

	@Inject
	public CommentDAO(OwnerCacheCleaner<Long, Comment> cacheCleaner) {
		super(Long.class, Comment.class);
		addListener(new TimestampListener<Long, Comment>());
		addListener(cacheCleaner);
	}

	/**
	 * Return a page of comments
	 * 
	 * @param postKey
	 *            post key
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 */
	public Page<Comment> page(Long postKey, int page, int pageSize) {
		String cacheKey = "postKey=" + postKey + ", page=" + page;
		Expression expr = Expr.and(Expr.eq("postKey", postKey),
				Expr.ne("status", ContentStatus.REMOVED));
		Page<Comment> p = find.page(page, pageSize, "createdOn desc", cacheKey,
				expr);
		return p;
	}

	public Page<Comment> getCommentsBy(String userKey, int page, int pageSize) {
		String cacheKey = "userKey=" + userKey + ", page=" + page;
		Expression expr = Expr.and(Expr.eq("created_by", userKey),
				Expr.ne("status", ContentStatus.REMOVED));
		return find.page(page, pageSize, "createdOn desc", cacheKey, expr);
	}

}
