package controllers;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.User;
import models.dao.PostRatingDAO;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.DynamicTemplateController;
import views.html.userShow;

public class UserController extends DynamicTemplateController {

	private static ALogger log = Logger.of(UserController.class);
	private PostRatingDAO postRatingDAO;
	private UserDAO userDAO;

	@Inject
	public UserController(UserDAO userDAO, PostRatingDAO postRatingDAO) {
		this.userDAO = userDAO;
		this.postRatingDAO = postRatingDAO;
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
					downVotes));
		}

		boolean selfPage = false;
		if (loginUser != null && userToShow != null
				&& (loginUser.getKey() + "").equals(userToShow.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(userToShow, selfPage, upVotes, downVotes));
	}

}
