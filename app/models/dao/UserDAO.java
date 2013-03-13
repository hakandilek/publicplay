package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.User;
import models.User.Status;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

@Singleton
public class UserDAO extends CachedDAO<String, User> {

	@Inject
	public UserDAO() {
		super(String.class, User.class);
		addListener(new TimestampListener<String, User>());
		//addListener(new UserDAOFollowCacheCleaner(userFollowDAO));
	}

	public Page<User> page(int page, int pageSize, Status status) {
		return page(page, pageSize, "lastLogin desc", "status", status);
	}

	public void saveAssociation(User u, String association) {
		u.saveManyToManyAssociations(association);
		String key = u.getKey();
		cacheFind().clean(key);
	}

}
