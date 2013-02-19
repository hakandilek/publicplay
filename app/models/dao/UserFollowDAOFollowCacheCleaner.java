package models.dao;

import models.UserFollow;
import models.UserFollowPK;
import play.utils.dao.DAOListener;

public class UserFollowDAOFollowCacheCleaner implements
		DAOListener<UserFollowPK, UserFollow>{

	private UserFollowDAO dao;

	public UserFollowDAOFollowCacheCleaner(UserFollowDAO dao) {
		this.dao = dao;
	}

	
	public void afterCreate(UserFollowPK key, UserFollow m) {
		dao.cleanCache(key.getSourceKey());
		dao.cleanCache(key.getTargetKey());
	}

	
	public void afterRemove(UserFollowPK key, UserFollow m) {
		dao.cleanCache(key.getSourceKey());
		dao.cleanCache(key.getTargetKey());
	}

	
	public void afterUpdate(UserFollowPK key, UserFollow m) {
	}

	
	public void beforeCreate(UserFollow m) {
	}

	
	public void beforeRemove(UserFollowPK key) {
	}

	
	public void beforeUpdate(UserFollowPK key, UserFollow m) {
	}
}
