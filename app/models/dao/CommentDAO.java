package models.dao;

import javax.inject.Inject;

import models.Comment;
import play.utils.cache.CachedFinder;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

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
		CachedFinder<Long, Comment> find = (CachedFinder<Long, Comment>) find();
		return find.page(page, pageSize, "createdOn desc", "postKey", postKey);
	}
   
}
