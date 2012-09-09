package controllers;

import java.util.List;

import models.Post;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.LoginProvider;
import securesocial.core.java.LoginProviderRegistry;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecureSocial.UserAware;
import securesocial.core.java.SocialUser;
import views.html.index;

public class HomeController extends Controller {

	@UserAware
	public static Result index() {
        SocialUser user = (SocialUser) ctx().args.get(SecureSocial.USER_KEY);
		List<LoginProvider> loginProviders = LoginProviderRegistry.getLoginProviders();
		List<Post> list = Post.all();
		return ok(index.render(list, user, loginProviders));
	}

}