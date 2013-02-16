package views.html.helper;

import models.User;
import controllers.HttpUtils;


public class UserHelper {

	public static boolean userPresent() {
		return HttpUtils.loginUser() != null;
	}

	public static User user() {
		return HttpUtils.loginUser();
	}

}
