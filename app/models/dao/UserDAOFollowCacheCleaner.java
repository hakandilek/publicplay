package models.dao;

import models.User;
import play.utils.dao.DAOListener;

public class UserDAOFollowCacheCleaner implements  DAOListener<String, User> {

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

	public void afterUpdate(String key, User m) {
	}

	
	public void beforeCreate(User m) {
	}

	
	public void beforeRemove(String key) {
	}

	
	public void beforeUpdate(String key, User m) {
	}
}
