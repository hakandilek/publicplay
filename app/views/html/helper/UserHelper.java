package views.html.helper;

import java.util.List;

import models.SecurityRole;
import models.User;
import controllers.HttpUtils;

public class UserHelper {

	public static boolean userPresent() {
		return HttpUtils.loginUser() != null;
	}

	public static User loginUser() {
		return HttpUtils.loginUser();
	}

	public static boolean isLoginUserFollowing(User user, List<String> loginUserFollowings) {
		return loginUserFollowings.contains(user.getKey());
	}

	public static String roleNames(User user) {
		StringBuilder sb = new StringBuilder();
		if (user != null) {
			List<SecurityRole> roles = user.getSecurityRoles();
			for (SecurityRole role : roles) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(role.getName());
			}
		}
		return sb.toString();
	}

	public static String roleKeys(User user) {
		StringBuilder sb = new StringBuilder("0");
		if (user != null) {
			List<SecurityRole> roles = user.getSecurityRoles();
			for (SecurityRole role : roles) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(role.getKey());
			}
		}
		return sb.toString();
	}
}
