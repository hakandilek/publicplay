package socialauth.core;

import play.mvc.Http.Context;
import play.mvc.Http.Session;
import socialauth.controllers.SocialLogin;
import socialauth.service.SocialUserService;

public class SocialUtils {

	public static SocialUser currentUser(Context ctx) {
		final Session session = ctx.session();
		if (session != null) {
			final String userKey = session.get(SocialLogin.USER_KEY);
			if (!emptyOrNull(userKey)) {
				final SocialUserService userService = SocialUserService
						.getInstance();
				final SocialUser user = userService.find(userKey);
				return user;
			}
		}
		return null;
	}

	public static String getUserKeyFromSession(Context ctx) {
		final Session session = ctx.session();
		if (session != null) {
			final String userKey = session.get(SocialLogin.USER_KEY);
			if (!emptyOrNull(userKey))
				return userKey;
		}
		return null;
	}

	public static boolean emptyOrNull(String string) {
		return string == null || string.trim().equals("") || "null".equals(string);
	}

}
