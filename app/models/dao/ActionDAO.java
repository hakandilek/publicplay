package models.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import models.Action;
import models.User;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

@Singleton
public class ActionDAO extends CachedDAO<Long, Action> {

	public ActionDAO() {
		super(Long.class, Action.class);
		addListener(new TimestampListener<Long, Action>());
	}

	public Page<Action> getActionsCreatedBy(List<String> usernames, int page,
			int pageSize) {
		return find.where().in("created_by", usernames)
				.orderBy("createdOn desc").findPagingList(pageSize)
				.getPage(page);
	}

	public Collection<Action> getActionsCreatedBy(User user) {
		if (user == null)
			return new ArrayList<>();
		return find.where().eq("created_by", user.getKey()).findSet();
	}

}
