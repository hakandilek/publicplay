package models.dao;

import models.User;
import models.UserReputation;
import play.utils.cache.InterimCache;
import play.utils.dao.DAOListenerAdapter;

public class UserReputationListener extends DAOListenerAdapter<Long, UserReputation> {

	final InterimCache<UserReputation> cache;

	public UserReputationListener(InterimCache<UserReputation> cache) {
		this.cache = cache;
	}

	@Override
	public void afterCreate(Long key, UserReputation rep) {
		if (rep != null) {
			User user = rep.getTargetUser();
			if (user != null) {
				String userKey = user.getKey();
				cache.set(userKey, rep);
			}
		}
	}

	@Override
	public void afterRemove(Long key, UserReputation rep) {
		if (rep != null) {
			User user = rep.getTargetUser();
			if (user != null) {
				String userKey = user.getKey();
				cache.set(userKey, null);
			}
		}
	}

	@Override
	public void afterUpdate(UserReputation rep) {
		if (rep != null) {
			User user = rep.getTargetUser();
			if (user != null) {
				String userKey = user.getKey();
				cache.set(userKey, rep);
			}
		}
	}

}
