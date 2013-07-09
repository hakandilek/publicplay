package models.dao;

import models.User;
import play.utils.dao.DAOListenerAdapter;

public class UserDAOFollowCacheCleaner extends DAOListenerAdapter<String, User> {

	private UserFollowDAO dao;

	public UserDAOFollowCacheCleaner(UserFollowDAO dao) {
		this.dao = dao;
	}

	public void afterCreate(String key, User m) {
		dao.cleanCache(key);
	}

	
	public void afterRemove(String key, User m) {
		dao.cleanCache(key);
	}
}
