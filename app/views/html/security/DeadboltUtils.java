package views.html.security;

import java.util.List;

import play.mvc.Http;
import be.objectify.deadbolt.Deadbolt;
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
		RoleHolder roleHolder = RequestUtils.getRoleHolder(
				PluginUtils.getHandler(), Http.Context.current());
		return roleHolder == null;
	}
}
