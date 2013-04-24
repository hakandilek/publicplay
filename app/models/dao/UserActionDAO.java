package models.dao;

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
import views.html.helper.UserHelper;

import com.avaje.ebean.Page;

import controllers.ActionConstants;

@Singleton
public class UserActionDAO extends CachedDAO<Long, Action> {

	private static ALogger log = Logger.of(UserActionDAO.class);

	ReputationValueDAO reputationValueDAO;

	private User loginUser;

	@Inject
	public UserActionDAO(ReputationValueDAO reputationValueDAO) {
		super(Long.class, Action.class);
		addListener(new TimestampListener<Long, Action>());
		this.reputationValueDAO = reputationValueDAO;
	}

	public void addUserAction(Model model, String actionName) {
		User user = internalGetLoginUser();
		Action userAction = new Action();
		userAction.setCreatedBy(user);
		userAction.setName(actionName);
		if (model instanceof Post) {
			userAction.setPost((Post) model);
		}else if(model instanceof Comment){
			userAction.setComment((Comment)model);
		}else if(model instanceof User){
			userAction.setUser((User)model);
		}
		this.create(userAction);
		user.setReputationValue(calculate(user));
		user.update();
	}

	private User internalGetLoginUser() {
		if (null == loginUser) {
			loginUser = UserHelper.loginUser();
		}
		return loginUser;
	}

	public int calculate(User userToShow) {
		int reputation = 0;
		if (reputationValueDAO.get(ActionConstants.RATING) != null
				&& reputationValueDAO.get(ActionConstants.CREATE_POST) != null) {
			
			int ratingValue = reputationValueDAO.get(ActionConstants.RATING).getValue();
			int postCreateValue = reputationValueDAO.get(ActionConstants.CREATE_POST)
					.getValue();
			for (Action action : userToShow.getActions()) {
				if (action.getName().equals(ActionConstants.CREATE_POST)) {
					int postRating = action.getPost().getRating();
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

	public void setUser(User user) {
		loginUser = user;
	}

	public Page<Action> getActionsCreatedBy(List<String> usernames, int page,
			int pageSize) {
		return find.where().in("created_by", usernames)
				.orderBy("createdOn desc").findPagingList(pageSize)
				.getPage(page);
	}

}
