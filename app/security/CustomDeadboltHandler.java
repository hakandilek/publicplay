package security;


import controllers.HttpUtils;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import plugins.GuicePlugin;
import views.html.errors.accessFailed;
import be.objectify.deadbolt.AbstractDeadboltHandler;
import be.objectify.deadbolt.DynamicResourceHandler;
import be.objectify.deadbolt.models.RoleHolder;


public class CustomDeadboltHandler extends AbstractDeadboltHandler {

	private static ALogger log = Logger.of(CustomDeadboltHandler.class);
	
	@Override
	public Result beforeRoleCheck(Context ctx) {
		return null;
	}

	@Override
	public DynamicResourceHandler getDynamicResourceHandler(Context ctx) {
		if (log.isDebugEnabled())
			log.debug("getDynamicResourceHandler() <-");
		
		return new CustomResourceHandler();
	}

	@Override
	public RoleHolder getRoleHolder(Context ctx) {
		if (log.isDebugEnabled())
			log.debug("getRoleHolder() <-");
		User user = httpUtils().loginUser(ctx);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

	@Override
	public Result onAccessFailure(Context ctx, String content) {
		if (log.isDebugEnabled())
			log.debug("onAccessFailure() <-");
		//set HTTP context before redirecting
		Http.Context.current.set(ctx);
		User user = httpUtils().loginUser(ctx);
		return forbidden(accessFailed.render(user));
	}

	private HttpUtils httpUtils() {
		return GuicePlugin.getInstance().getInstance(HttpUtils.class);
	}

}
