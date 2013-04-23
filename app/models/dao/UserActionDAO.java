package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Post;
import models.Action;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.utils.dao.CachedDAO;
import views.html.helper.UserHelper;

@Singleton
public class UserActionDAO extends CachedDAO<Long, Action> {

	private static ALogger log = Logger.of(UserActionDAO.class);

	ReputationValueDAO reputationValueDAO;

	private User loginUser;

	@Inject
	public UserActionDAO(ReputationValueDAO reputationValueDAO) {
		super(Long.class, Action.class);
		this.reputationValueDAO = reputationValueDAO;
	}

	public void addUserAction(Post post, String actionName) {
		User user = internalGetLoginUser();
		Action userAction = new Action();
		userAction.setCreatedBy(user);
		userAction.setName(actionName);
		userAction.setPost(post);
		this.create(userAction);
		user.setReputationValue(calculate(user));
	}

	private User internalGetLoginUser() {
		if (null == loginUser) {
			loginUser = UserHelper.loginUser();
		}
		return loginUser;
	}

	public int calculate(User userToShow) {
		int reputation = 0;
		if (reputationValueDAO.get("rating") != null
				&& reputationValueDAO.get("createPost") != null) {

			int ratingValue = reputationValueDAO.get("rating").getValue();
			int postCreateValue = reputationValueDAO.get("createPost")
					.getValue();
			for (Action action : userToShow.getActions()) {
				if (action.getName().equals("createPost")) {
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

}
