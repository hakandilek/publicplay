/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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