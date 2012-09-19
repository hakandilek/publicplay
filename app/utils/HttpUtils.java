package utils;

import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import socialauth.controllers.SocialLogin;
import socialauth.core.SocialUser;

public class HttpUtils {

	private static ALogger log = Logger.of(HttpUtils.class);
	
	public static String selfURL() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * extract login user from the context if available
	 * @param ctx http context
	 * @return login user, or null
	 */
	public static User loginUser(Context ctx) {
		final SocialUser su = (SocialUser) ctx.args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("su : " + su);
		User user = null;
		if (su != null)
			user  = new User(su);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

}
