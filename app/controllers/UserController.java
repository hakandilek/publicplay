package controllers;

import java.util.Set;
import java.util.TreeSet;

import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import utils.HttpUtils;
import views.html.userShow;

public class UserController extends Controller {

	private static ALogger log = Logger.of(UserController.class);

	@SocialAware
	@RestrictApproved
	public static Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("show() <-");
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		User userToBeShowed = null;
		if (null != key) {
			userToBeShowed = User.get(key);
		}
		final Set<Long> upVotes = userToBeShowed == null ? new TreeSet<Long>() : userToBeShowed.getUpVotedPostKeys();
		final Set<Long> downVotes = userToBeShowed == null ? new TreeSet<Long>() : userToBeShowed.getDownVotedPostKeys();

		User loggedInUser = HttpUtils.loginUser(ctx());
		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		if (loggedInUser == null || userToBeShowed == null) {
			return badRequest(userShow.render(loggedInUser, userToBeShowed,
					false,upVotes, downVotes));
		}
		boolean selfPage = false;
		if (loggedInUser != null && userToBeShowed != null
				&& (loggedInUser.getKey() + "").equals(userToBeShowed.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(loggedInUser, userToBeShowed, selfPage,upVotes, downVotes));
	}

	@Secure
	public static Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");

		User loggedInUser = HttpUtils.loginUser(ctx());
		final Set<Long> upVotes = loggedInUser == null ? new TreeSet<Long>() : loggedInUser.getUpVotedPostKeys();
		final Set<Long> downVotes = loggedInUser == null ? new TreeSet<Long>() : loggedInUser.getDownVotedPostKeys();

		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		boolean selfPage = true;

		return ok(userShow.render(loggedInUser, loggedInUser, selfPage,upVotes, downVotes));
	}
}