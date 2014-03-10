package security;

import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import controllers.HttpUtils;
import controllers.routes;

public class AuthenticatedAction extends Action<Authenticated> {
	private static ALogger log = Logger.of(AuthenticatedAction.class);

	@Override
	public F.Promise<SimpleResult> call(Context ctx) throws Throwable {
		if (log.isDebugEnabled())
			log.debug("call() <-");
		User user = HttpUtils.loginUser(ctx);
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		if (user != null) {
			return delegate.call(ctx);
		} else {
			// there is no user in the backing store matching the credentials
			// sent by the client. Remove the credentials from the session
			if (log.isDebugEnabled())
				log.debug("redirecting to logout page");
			return F.Promise.promise(new F.Function0<SimpleResult>() {
				@Override
				public SimpleResult apply() throws Throwable {
					return redirect(routes.App.logout());
				}
			});
		}
	}

}
