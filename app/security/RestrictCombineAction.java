package security;

import java.util.List;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Result;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.actions.AbstractRestrictiveAction;
import be.objectify.deadbolt.models.Permission;
import be.objectify.deadbolt.models.RoleHolder;

public class RestrictCombineAction extends
		AbstractRestrictiveAction<RestrictCombine> {

	private static ALogger log = Logger.of(RestrictCombineAction.class);

	@Override
	public Result applyRestriction(Context ctx, DeadboltHandler deadboltHandler)
			throws Throwable {
		Result result;
		if (isAllowed(ctx, deadboltHandler)) {
			markActionAsAuthorised(ctx);
			result = delegate.call(ctx);
		} else {
			markActionAsUnauthorised(ctx);
			result = onAccessFailure(deadboltHandler, configuration.content(),
					ctx);
		}

		return result;
	}

	private boolean isAllowed(Context ctx, DeadboltHandler deadboltHandler) {
		if (log.isDebugEnabled())
			log.debug("isAllowed() <-");

		RoleHolder roleHolder = getRoleHolder(ctx, deadboltHandler);

		boolean roleOk = false;
		if (roleHolder != null) {
			roleOk = checkRole(roleHolder, configuration.roles());
		}

		if (!roleOk) {
			roleOk = checkPermission(roleHolder, configuration.with(), ctx);
		}

		return roleOk;
	}

	private boolean checkPermission(RoleHolder roleHolder, Class<? extends RequestPermission> permissionClass,
			Context ctx) {
		if (log.isDebugEnabled())
			log.debug("checkPermission() <-");
		
		RequestPermission permission = null;
		try {
			permission = permissionClass.newInstance();
		} catch (Exception e) {
			log.error("cannot create permission", e);
			return false;
		}
		
		List<? extends Permission> permissions = roleHolder.getPermissions();
		
		Request request = ctx.request();
		if (log.isDebugEnabled())
			log.debug("request : " + request);
		String path = request.path();
		if (log.isDebugEnabled())
			log.debug("path : " + path);
		return permission.isAllowed(request, permissions);
	}

	@Override
	public Class<? extends DeadboltHandler> getDeadboltHandlerClass() {
		return configuration.handler();
	}
}