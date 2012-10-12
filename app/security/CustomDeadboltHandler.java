package security;


import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import play.mvc.Result;
import utils.HttpUtils;
import views.html.accessFailed;
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
		User user = HttpUtils.loginUser(ctx);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

	@Override
	public Result onAccessFailure(Context ctx, String content) {
		if (log.isDebugEnabled())
			log.debug("onAccessFailure() <-");
		
		return forbidden(accessFailed.render());
	}

}
