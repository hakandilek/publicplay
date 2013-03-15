package socialauth.core;

import play.Logger;
import play.Logger.ALogger;
import play.i18n.Messages;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import controllers.AuthController;
import controllers.routes;

public class SecureAction extends Action<Secure> {
	private static ALogger log = Logger.of(SecureAction.class);

	@Override
	public Result call(Context ctx) throws Throwable {
		if (log.isDebugEnabled())
			log.debug("call() <-");
		
		try {
			// Have to set ctx explicitly
			Context.current.set(ctx);
			String userKey = SocialUtils.getUserKeyFromSession(ctx);
			if (log.isDebugEnabled())
				log.debug("userKey : " + userKey);
			
			if (userKey == null) {
				final String requestURI = ctx.request().uri();
				if (log.isDebugEnabled()) {
					log.debug("Anonymous user trying to access : "
							+ requestURI);
				}
				ctx.flash().put("error",
						Messages.get("securesocial.loginRequired"));
				
				if (log.isDebugEnabled())
					log.debug("login required");
				
				if (!SocialUtils.emptyOrNull(requestURI))
				{
					if (log.isDebugEnabled())
						log.debug("save original URL in session = " + requestURI);
					ctx.session().put(AuthController.ORIGINAL_URL, requestURI);
				}
				if (log.isDebugEnabled())
					log.debug("redirecting to login page");
				return redirect(routes.App.login());
			} else {
				SocialUser user = SocialUtils.currentUser(ctx);
				if (log.isDebugEnabled())
					log.debug("user : " + user);
				if (user != null) {
					ctx.args.put(AuthController.USER, user);
					if (log.isDebugEnabled())
						log.debug("calling delegate action");
					return delegate.call(ctx);
				} else {
					// there is no user in the backing store matching the credentials 
					// sent by the client. Remove the credentials from the session
					if (log.isDebugEnabled())
						log.debug("redirecting to logout page");
					return redirect(routes.App.logout());
				}
			}
		} finally {
			// leave it null as it was before, just in case.
			if (log.isDebugEnabled())
				log.debug("cleaning context");
			Http.Context.current.set(null);
		}
	}

}
