package socialauth.core;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;

public class SocialAwareAction extends Action<SocialAware> {

	@Override
	public Result call(Context ctx) throws Throwable {
		SocialUser user = SocialUtils.currentUser(ctx);
		if (user != null) {
			ctx.args.put(SocialLogin.USER_KEY, user);
		}
		return delegate.call(ctx);
	}


}
