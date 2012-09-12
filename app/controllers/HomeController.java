package controllers;

import java.util.List;

import models.Post;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;
import socialauth.core.SocialAware;
import socialauth.core.SocialUser;
import views.html.index;

public class HomeController extends Controller {

	private static ALogger log = Logger.of(HomeController.class);
	
	@SocialAware
	public static Result index() {
		if (log.isDebugEnabled())
			log.debug("index() <-");
		
		final SocialUser user = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		List<Post> list = Post.all();
		return ok(index.render(list, user));
	}

}