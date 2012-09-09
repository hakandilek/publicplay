package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.LoginProvider;
import securesocial.core.java.LoginProviderRegistry;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SocialUser;
import views.html.userInfo;
import views.html.userLogin;

public class UserController extends Controller {

    @SecureSocial.Secured
	public static Result info() {
        SocialUser user = (SocialUser) ctx().args.get(SecureSocial.USER_KEY);
		List<LoginProvider> providers = LoginProviderRegistry.getLoginProviders();
		return ok(userInfo.render(user, providers));
	}

	public static Result login() {
		List<LoginProvider> providers = LoginProviderRegistry.getLoginProviders();
		return ok(userLogin.render(providers));
	}
}
