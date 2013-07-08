package models.dao;

import java.util.concurrent.Callable;

import karma.model.ReputationHolderStore;
import models.User;
import models.UserReputation;
import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;

public class UserReputationDAO extends CachedDAO<Long, UserReputation> implements
		ReputationHolderStore<User, UserReputation> {

	protected InterimCache<UserReputation> targetUserCache = new InterimCache<UserReputation>(
			"UserReputationTargetUserCache", 86400);// 24 hrs

	public UserReputationDAO() {
		super(Long.class, UserReputation.class);
		addListener(new UserReputationListener(targetUserCache));
	}

	@Override
	public UserReputation get(User user) {
		final String userKey = user.getKey();
		return targetUserCache.get(userKey, new Callable<UserReputation>() {
			public UserReputation call() throws Exception {
				return find.where().eq("target_user_key", userKey).findUnique();
			}
		});
	}

	@Override
	public UserReputation create(User user) {
		UserReputation ur = new UserReputation(user, 0);
		create(ur);
		return ur;
	}

}
