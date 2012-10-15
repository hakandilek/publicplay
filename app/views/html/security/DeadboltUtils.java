package views.html.security;

import java.util.List;

import be.objectify.deadbolt.Deadbolt;
import be.objectify.deadbolt.PatternType;

public class DeadboltUtils {

	public static boolean viewRestrictCombine(List<String[]> roles,
			String withPattern, PatternType patternType) throws Throwable {
		boolean hasAccess = Deadbolt.viewRestrict(roles);
		if (!hasAccess) {
			hasAccess = Deadbolt.viewPattern(withPattern, patternType);
		}
		return hasAccess;
	}
}
