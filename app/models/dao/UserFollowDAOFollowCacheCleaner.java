package models.dao;

import models.UserFollow;
import models.UserFollowPK;
import play.utils.dao.DAOListenerAdapter;

public class UserFollowDAOFollowCacheCleaner extends DAOListenerAdapter<UserFollowPK, UserFollow>{

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
	
}
