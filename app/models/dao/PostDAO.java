package models.dao;

import models.Post;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampDAO;

public class PostDAO extends TimestampDAO<Long, Post> {

	public PostDAO() {
		super(new CachedDAO<Long, Post>(Long.class, Post.class));
	}

}
