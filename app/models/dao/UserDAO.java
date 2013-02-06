package models.dao;

import models.User;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

public class UserDAO extends CachedDAO<String, User> {

	public UserDAO() {
		super(String.class, User.class);
		addListener(new TimestampListener<String, User>());
	}

}
