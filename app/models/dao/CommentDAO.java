package models.dao;

import models.Comment;
import models.User;
import play.utils.cache.CachedFinder;
import play.utils.dao.CachedDAO;
import play.utils.dao.EntityNotFoundException;
import play.utils.dao.TimestampDAO;

import com.avaje.ebean.Page;

public class CommentDAO extends TimestampDAO<Long, Comment> {

	public CommentDAO() {
		super(new CachedDAO<Long, Comment>(Long.class, Comment.class));
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
   
	public Long create(Comment comment) {
		Long key = super.create(comment);
		
		// clean user cache for a backlink update
		User owner = comment.getCreatedBy();
		if (owner != null)
			User.find.clean(owner.getKey());
		return key;
	}

	public void remove(Long key) throws EntityNotFoundException {
		Comment comment = find().ref(key);
		super.remove(key);
		
		// clean user cache for a backlink update
		User owner = comment.getCreatedBy();
		if (owner != null)
			User.find.clean(owner.getKey());
	}

	public void update(Long key, Comment comment) {
		super.update(key, comment);
		
		// clean user cache for a backlink update
		User owner = comment.getCreatedBy();
		if (owner != null)
			User.find.clean(owner.getKey());
	}
	

}
