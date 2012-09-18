package controllers;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.SocialAware;

public class HomeController extends Controller {

	private static ALogger log = Logger.of(HomeController.class);

	/**
	 * Display the index page with paginated list of posts.
	 */
	@SocialAware
	public static Result index() {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		return PostController.list(0, "createdOn", "desc", "");
	}

}