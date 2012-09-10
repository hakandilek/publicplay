package controllers;

import java.util.List;


import models.Post;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;
import socialauth.core.SocialAware;
import socialauth.core.SocialUser;
import views.html.index;

public class HomeController extends Controller {

	@SocialAware
	public static Result index() {
		final SocialUser user = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		List<Post> list = Post.all();
		return ok(index.render(list, user));
	}

}