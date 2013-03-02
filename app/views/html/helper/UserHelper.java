package views.html.helper;

import java.util.List;

import models.User;
import controllers.HttpUtils;


public class UserHelper {

	public static boolean userPresent() {
		return HttpUtils.loginUser() != null;
	}

	public static User loginUser() {
		return HttpUtils.loginUser();
	}
	
	public static boolean isLoginUserFollowing(User user,List<String> loginUserFollowings){
		return loginUserFollowings.contains(user.getKey());
	}

}
