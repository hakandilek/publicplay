package socialauth.core;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class SocialAwareAction extends Action<SocialAware> {

	@Override
	public Result call(Context ctx) throws Throwable {
		SocialUser user = currentUser(ctx);
		if (user != null) {
			ctx.args.put(SocialAware.USER_KEY, user);
		}
		return delegate.call(ctx);
	}

	private SocialUser currentUser(Context ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
