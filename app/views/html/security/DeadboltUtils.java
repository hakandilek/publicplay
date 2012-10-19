package views.html.security;

import java.util.List;

import play.mvc.Http;
import play.mvc.Http.Context;
import be.objectify.deadbolt.Deadbolt;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.PatternType;
import be.objectify.deadbolt.models.RoleHolder;
import be.objectify.deadbolt.utils.PluginUtils;
import be.objectify.deadbolt.utils.RequestUtils;

public class DeadboltUtils {

	public static boolean viewRestrictCombine(List<String[]> roles,
			String withPattern, PatternType patternType) throws Throwable {
		boolean hasAccess = Deadbolt.viewRestrict(roles);
		if (!hasAccess) {
			hasAccess = Deadbolt.viewPattern(withPattern, patternType);
		}
		return hasAccess;
	}

	/**
	 * Used for roleHolderNotPresent tags in the template.
	 * 
	 * @return true if the view can be accessed, otherwise false
	 */
	public static boolean viewRoleHolderNotPresent() throws Throwable {
		DeadboltHandler handler = PluginUtils.getHandler();
		Context ctx = Http.Context.current();
		RoleHolder roleHolder = RequestUtils.getRoleHolder(
				handler, ctx);
		return roleHolder == null;
	}
}
