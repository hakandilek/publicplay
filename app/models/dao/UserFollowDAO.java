package models.dao;

import javax.inject.Singleton;

import models.UserFollow;
import models.UserFollowPK;
import play.utils.dao.CachedDAO;

@Singleton
public class UserFollowDAO extends CachedDAO<UserFollowPK, UserFollow> {

	public UserFollowDAO() {
		super(UserFollowPK.class, UserFollow.class);
	}

	public UserFollow get(String sourceKey, String targetKey) {
		UserFollowPK key = new UserFollowPK(sourceKey, targetKey);
		return find().byId(key);
	}

}
