package views.html.helper;

import models.User;
import controllers.Admin;


public class UserHelper {

	public static boolean userPresent() {
		if (Admin.httpUtils != null) {
			return Admin.httpUtils.loginUser() != null;
		}
		return false;
	}

	public static User user() {
		if (Admin.httpUtils != null) {
			return Admin.httpUtils.loginUser();
		}
		return null;
	}

}
