package security;

import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;
import views.html.errors.accessFailed;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import controllers.HttpUtils;

public class CustomDeadboltHandler extends AbstractDeadboltHandler {

	private static ALogger log = Logger.of(CustomDeadboltHandler.class);

	public DynamicResourceHandler getDynamicResourceHandler(Context ctx) {
		if (log.isDebugEnabled())
			log.debug("getDynamicResourceHandler() <-");

		return new CustomResourceHandler();
	}

	public Subject getSubject(Context ctx) {
		if (log.isDebugEnabled())
			log.debug("getSubject() <-");
		User user = HttpUtils.loginUser(ctx);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

	@Override
	public Promise<SimpleResult> onAuthFailure(Context ctx, String content) {
		Http.Context.current.set(ctx);
		// you can return any result from here - forbidden, etc
		return F.Promise.promise(new F.Function0<SimpleResult>() {
			@Override
			public SimpleResult apply() throws Throwable {
				return ok(accessFailed.render());
			}
		});
	}

	@Override
	public Promise<SimpleResult> beforeAuthCheck(Context ctx) {
		return F.Promise.pure(null);
	}
}
