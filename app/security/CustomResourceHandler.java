package security;

import java.util.Iterator;
import java.util.List;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Subject;

public class CustomResourceHandler implements DynamicResourceHandler {

	private static ALogger log = Logger.of(CustomResourceHandler.class);

	public boolean checkPermission(String permission, DeadboltHandler handler,
			Context ctx) {
		if (log.isDebugEnabled())
			log.debug("checkPermission() <-");
		if (log.isDebugEnabled())
			log.debug("permission : " + permission);
		
		boolean permissionOk = false;
		Subject roleHolder = handler.getSubject(ctx);

		if (roleHolder != null) {
			List<? extends Permission> permissions = roleHolder
					.getPermissions();
			for (Iterator<? extends Permission> iterator = permissions
					.iterator(); !permissionOk && iterator.hasNext();) {
				Permission perm = iterator.next();
				permissionOk = perm.getValue().contains(permission);
			}
		}

		return permissionOk;
	}

	public boolean isAllowed(String name, String meta, DeadboltHandler handler,
			Context ctx) {
		if (log.isDebugEnabled())
			log.debug("isAllowed() <-");
		if (log.isDebugEnabled())
			log.debug("name : " + name);
		if (log.isDebugEnabled())
			log.debug("meta : " + meta);
		boolean permissionOk = false;
		Subject roleHolder = handler.getSubject(ctx);
		if (log.isDebugEnabled())
			log.debug("roleHolder : " + roleHolder);

		return permissionOk;
	}

}
