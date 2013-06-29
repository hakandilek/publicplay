package models.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Action;
import models.Comment;
import models.Post;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.db.ebean.Model;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Page;

import controllers.ActionConstants;

@Singleton
public class UserActionDAO extends CachedDAO<Long, Action> {

	private static ALogger log = Logger.of(UserActionDAO.class);

	ReputationValueDAO reputationValueDAO;

	@Inject
	public UserActionDAO(ReputationValueDAO reputationValueDAO) {
		super(Long.class, Action.class);
		addListener(new TimestampListener<Long, Action>());
		this.reputationValueDAO = reputationValueDAO;
	}

	public void addUserAction(User user, Model model, String actionName) {
		Action userAction = new Action();
		userAction.setCreatedBy(user);
		userAction.setName(actionName);
		if (model instanceof Post) {
			userAction.setTargetPost((Post) model);
		} else if (model instanceof Comment) {
			userAction.setTargetComment((Comment) model);
		} else if (model instanceof User) {
			userAction.setTargetUser((User) model);
		}
		this.create(userAction);
		user.setReputationValue(calculate(user));
		user.update();
	}

	public int calculate(User userToShow) {
		int reputation = 0;
		if (reputationValueDAO.get(ActionConstants.RATING) != null
				&& reputationValueDAO.get(ActionConstants.CREATE_POST) != null) {
			
			int ratingValue = reputationValueDAO.get(ActionConstants.RATING).getValue();
			int postCreateValue = reputationValueDAO.get(ActionConstants.CREATE_POST)
					.getValue();
			Collection<Action> actions = getActionsCreatedBy(userToShow);
			for (Action action : actions) {
				if (action.getName().equals(ActionConstants.CREATE_POST)) {
					int postRating = action.getTargetPost().getRating();
					reputation += postRating * ratingValue;
					reputation += postCreateValue;
				}
			}
		} else {
			if (log.isDebugEnabled())
				log.error(String
						.format("There exist no reputation value for createPost or rating"));
		}
		return reputation;
	}

	public Collection<Action> getActionsCreatedBy(User user) {
		if (user == null) return new ArrayList<>();
		return find.where().eq("created_by", user.getKey()).findSet();
	}

	public Page<Action> getActionsCreatedBy(List<String> usernames, int page,
			int pageSize) {
		return find.where().in("created_by", usernames)
				.orderBy("createdOn desc").findPagingList(pageSize)
				.getPage(page);
	}

}
