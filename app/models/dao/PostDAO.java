package models.dao;

import models.Post;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

public class PostDAO extends CachedDAO<Long, Post> {

	public PostDAO() {
		super(Long.class, Post.class);
		addListener(new TimestampListener<Long, Post>());
	}

}
