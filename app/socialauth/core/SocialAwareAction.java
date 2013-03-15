package socialauth.core;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import controllers.AuthController;

public class SocialAwareAction extends Action<SocialAware> {

	private static ALogger log = Logger.of(SocialAwareAction.class);

	@Override
	public Result call(Context ctx) throws Throwable {
		if (log.isDebugEnabled())
			log.debug("call() <-");
		SocialUser user = SocialUtils.currentUser(ctx);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		if (user != null) {
			ctx.args.put(AuthController.USER, user);
		}
		if (log.isDebugEnabled())
			log.debug("calling delegate action");
		return delegate.call(ctx);
	}
	
}
