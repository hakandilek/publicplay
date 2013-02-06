package models.dao;

import models.Comment;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

public class CommentDAO extends CachedDAO<Long, Comment> {

	public CommentDAO() {
		super(Long.class, Comment.class);
		addListener(new TimestampListener<Long, Comment>());
	}

}
