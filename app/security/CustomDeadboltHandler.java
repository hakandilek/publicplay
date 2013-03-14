package security;


import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.errors.accessFailed;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Subject;
import controllers.HttpUtils;


public class CustomDeadboltHandler extends AbstractDeadboltHandler {

	private static ALogger log = Logger.of(CustomDeadboltHandler.class);
	
	public Result beforeAuthCheck(Http.Context context) {
		return null;
	}

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

	public Result onAuthFailure(Context ctx, String content) {
		if (log.isDebugEnabled())
			log.debug("onAuthFailure() <-");
		//set HTTP context before redirecting
		Http.Context.current.set(ctx);
		return forbidden(accessFailed.render());
	}
}
