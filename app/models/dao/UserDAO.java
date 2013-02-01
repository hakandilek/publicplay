package models.dao;

import models.User;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampDAO;

public class UserDAO extends TimestampDAO<String, User> {

	public UserDAO() {
		super(new CachedDAO<String, User>(String.class, User.class));
	}

}
