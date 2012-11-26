package controllers;

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

		User loggedInUser = HttpUtils.loginUser(ctx());
		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		if (loggedInUser == null || userToBeShowed == null) {
			return badRequest(userShow.render(loggedInUser, userToBeShowed,
					false));
		}
		boolean selfPage = false;
		if (loggedInUser != null && userToBeShowed != null
				&& (loggedInUser.getKey() + "").equals(userToBeShowed.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(loggedInUser, userToBeShowed, selfPage));
	}

	@Secure
	public static Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");

		User loggedInUser = HttpUtils.loginUser(ctx());

		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		boolean selfPage = true;

		return ok(userShow.render(loggedInUser, loggedInUser, selfPage));
	}
}