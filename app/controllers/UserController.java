package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.userInfo;
import views.html.userLogin;

public class UserController extends Controller {

	public static Result info() {
		return ok(userInfo.render());
	}

	public static Result login() {
		return ok(userLogin.render());
	}
}
