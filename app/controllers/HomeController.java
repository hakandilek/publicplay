package controllers;

import java.util.List;

import models.Post;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.SocialAware;
import utils.HttpUtils;
import views.html.index;

public class HomeController extends Controller {

	private static ALogger log = Logger.of(HomeController.class);
	
	@SocialAware
	public static Result index() {
		if (log.isDebugEnabled())
			log.debug("index() <-");
		
		User user = HttpUtils.loginUser(ctx());
		
		List<Post> list = Post.all();
		return ok(index.render(list, user));
	}

}