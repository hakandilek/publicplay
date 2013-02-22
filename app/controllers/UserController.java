package controllers;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.User;
import models.UserFollow;
import models.dao.PostRatingDAO;
import models.dao.UserDAO;
import models.dao.UserFollowDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.DynamicTemplateController;
import views.html.userShow;

public class UserController extends DynamicTemplateController {

	private static ALogger log = Logger.of(UserController.class);
	private PostRatingDAO postRatingDAO;
	private UserDAO userDAO;
	private UserFollowDAO userFollowDAO;

	@Inject
	public UserController(UserDAO userDAO, PostRatingDAO postRatingDAO,
			UserFollowDAO userFollowDAO) {
		this.userDAO = userDAO;
		this.postRatingDAO = postRatingDAO;
		this.userFollowDAO = userFollowDAO;
	}

	public Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("show() <- " + key);
		User loginUser = HttpUtils.loginUser();
		User userToShow = null;
		if (null != key)
			userToShow = userDAO.get(key);
		return show(loginUser, userToShow);
	}

	public Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");
		User loginUser = HttpUtils.loginUser(ctx());
		return show(loginUser, loginUser);
	}

	private Result show(User loginUser, User userToShow) {

		Set<Long> upVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(userToShow);
		Set<Long> downVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(userToShow);

		if (log.isDebugEnabled())
			log.debug("user : " + loginUser);
		if (loginUser == null || userToShow == null) {
			return badRequest(userShow.render(userToShow, false, upVotes,
					downVotes, false,0,0));
		}

		boolean selfPage = false;
		if (loginUser != null && userToShow != null
				&& (loginUser.getKey() + "").equals(userToShow.getKey())) {
			selfPage = true;
		}

		boolean following = false;
		if (loginUser != null && userToShow != null) {
			String sourceKey = loginUser.getKey();
			String targetKey = userToShow.getKey();
			UserFollow follow = userFollowDAO.get(sourceKey, targetKey);
			following = follow != null;
		}
		int followerCount = userFollowDAO.getFollowerCount(userToShow);
		int followingCount = userFollowDAO.getFollowingCount(userToShow);

		return ok(userShow.render(userToShow, selfPage, upVotes, downVotes,
				following, followerCount, followingCount));
	}

}
