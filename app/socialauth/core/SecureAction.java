package socialauth.core;

import play.Logger;
import play.i18n.Messages;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;
import socialauth.controllers.routes;

public class SecureAction extends Action<Secure> {

	@Override
	public Result call(Context ctx) throws Throwable {
		try {
			// Have to set ctx explicitly
			Context.current.set(ctx);
			String userKey = SocialUtils.getUserKeyFromSession(ctx);
			Logger.info("userKey = " + userKey);
			if (userKey == null) {
				final String requestURI = ctx.request().uri();
				if (Logger.isDebugEnabled()) {
					Logger.debug("Anonymous user trying to access : "
							+ requestURI);
				}
				ctx.flash().put("error",
						Messages.get("securesocial.loginRequired"));
				ctx.session().put(SocialLogin.ORIGINAL_URL, requestURI);
				return redirect(routes.SocialLogin.login());
			} else {
				SocialUser user = SocialUtils.currentUser(ctx);
				if (user != null) {
					ctx.args.put(SocialLogin.USER_KEY, user);
					return delegate.call(ctx);
				} else {
					// there is no user in the backing store matching the credentials 
					// sent by the client. Remove the credentials from the session
					return redirect(routes.SocialLogin.logout());
				}
			}
		} finally {
			// leave it null as it was before, just in case.H
			Http.Context.current.set(null);
		}
	}

}
