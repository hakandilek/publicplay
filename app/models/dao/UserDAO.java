package models.dao;

import com.avaje.ebean.Page;

import models.User;
import models.User.Status;
import play.utils.cache.CachedFinder;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampDAO;

public class UserDAO extends TimestampDAO<String, User> {

	private static CachedFinder<String, User> find;

	public UserDAO() {
		super(new CachedDAO<String, User>(String.class, User.class));
		find = (CachedFinder<String, User>) find();
	}

	public static Page<User> page(int page, int pageSize, Status status) {
		return find.where().eq("status", status).orderBy("lastLogin desc")
				.findPagingList(pageSize).getPage(page);
	}

}
