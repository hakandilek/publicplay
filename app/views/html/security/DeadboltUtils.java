package views.html.security;

import static be.objectify.deadbolt.java.DeadboltViewSupport.viewPattern;
import static be.objectify.deadbolt.java.DeadboltViewSupport.viewRestrict;

import java.util.List;

import play.mvc.Http;
import play.mvc.Http.Context;
import security.Approvable;
import be.objectify.deadbolt.core.PatternType;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.utils.PluginUtils;
import be.objectify.deadbolt.java.utils.RequestUtils;

public class DeadboltUtils {

	public static boolean viewRestrictCombine(List<String[]> roles, String withPattern, PatternType patternType)
			throws Throwable {
		boolean hasAccess = viewRestrict(roles, null);
		if (!hasAccess) {
			hasAccess = viewPattern(withPattern, patternType, null);
		}
		return hasAccess;
	}

	/**
	 * Used for roleHolderNotPresent tags in the template.
	 * 
	 * @return true if the view can be accessed, otherwise false
	 */
	public static boolean viewSubjectNotPresent() throws Throwable {
		DeadboltHandler handler = PluginUtils.getDeadboltHandler();
		Context ctx = Http.Context.current();
		Subject roleHolder = RequestUtils.getSubject(handler, ctx);
		return roleHolder == null;
	}

	public static boolean viewRestrictApproved() throws Throwable {
		DeadboltHandler handler = PluginUtils.getDeadboltHandler();
		Context ctx = Http.Context.current();
		Subject roleHolder = RequestUtils.getSubject(handler, ctx);
		return isSubjectApproved(roleHolder);
	}

	public static boolean viewRestrictNotApproved() throws Throwable {
		return !viewRestrictApproved();
	}

	public static boolean isSubjectApproved(Subject roleHolder) {
		boolean approved = false;
		if (roleHolder != null && roleHolder instanceof Approvable) {
			Approvable approvable = (Approvable) roleHolder;

			if (approvable.isApproved())
				approved = true;
		}
		return approved;
	}
}
