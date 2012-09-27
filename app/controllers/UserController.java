package controllers;

import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import socialauth.core.SocialUser;
import views.html.userShow;

public class UserController extends Controller {

	private static ALogger log = Logger.of(UserController.class);
	
	@SocialAware
	public static Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("index() <-");
		if (log.isDebugEnabled())
			log.debug("key : " + key);
		
		final SocialUser self = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("self : " + self);
		
		User user = User.get(key);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		if (user == null) {
			return badRequest(userShow.render(user, false));
		}
		boolean selfPage = false;
		if (user != null && self != null && (user.getKey() + "").equals(self.getUserKey())) {
			selfPage  = true;
		}
		
		return ok(userShow.render(user, selfPage));
	}

	@Secure
	public static Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("index() <-");
		
		final SocialUser self = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("self : " + self);
		
		User user = new User(self);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		boolean selfPage = true;
		
		return ok(userShow.render(user, selfPage));
	}
}