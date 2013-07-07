package models.dao;

import karma.model.ReputationHolderStore;
import models.User;
import models.UserReputation;
import play.utils.dao.CachedDAO;

public class UserReputationDAO extends CachedDAO<Long, UserReputation>
		implements ReputationHolderStore<User, UserReputation> {

	public UserReputationDAO() {
		super(Long.class, UserReputation.class);
	}

	@Override
	public UserReputation get(User user) {
		//TODO:use cache
		return find.where().eq("target_user_key", user.getKey()).findUnique();
	}

	@Override
	public UserReputation create(User subject) {
		UserReputation ur = new UserReputation(subject, 0);
		create(ur);
		return ur;
	}

}
