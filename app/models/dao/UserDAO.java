package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.User;
import models.User.Status;
import play.utils.cache.CachedFinder;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

@Singleton
public class UserDAO extends CachedDAO<String, User> {

	private static CachedFinder<String, User> find;

	@Inject
	public UserDAO(UserFollowDAO userFollowDAO) {
		super(String.class, User.class);
		addListener(new TimestampListener<String, User>());
		addListener(new UserDAOFollowCacheCleaner(userFollowDAO));
		find = cacheFind();
	}

	public static Page<User> page(int page, int pageSize, Status status) {
		return find.where().eq("status", status).orderBy("lastLogin desc")
				.findPagingList(pageSize).getPage(page);
	}

}
