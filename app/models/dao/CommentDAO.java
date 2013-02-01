package models.dao;

import models.Comment;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampDAO;

public class CommentDAO extends TimestampDAO<Long, Comment> {

	public CommentDAO() {
		super(new CachedDAO<Long, Comment>(Long.class, Comment.class));
	}

}
