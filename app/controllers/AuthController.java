package controllers;

import javax.inject.Inject;

import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.userLogin;

import com.feth.play.module.pa.controllers.Authenticate;

public class AuthController extends Controller {
	public static final String FLASH_ERROR_KEY = "error";
	
	private static ALogger log = Logger.of(AuthController.class);

	/** session key for authenticated user key */
	public static final String USER_KEY = "userKey";

	/** session key for authenticated user */
	public static final String USER = "user";

	public static final String ORIGINAL_URL = "originalURL";

	UserDAO userDAO;

	@Inject
	public AuthController(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public Result login() {
		if (log.isDebugEnabled())
			log.debug("login() <-");

		return ok(userLogin.render());
	}

	public Result logout() {
		return Authenticate.logout();
	}

	public Result authenticate(String provider) {
		if (log.isDebugEnabled())
			log.debug("authenticate() with provider = " + provider);

		return Authenticate.authenticate(provider);
	}

	public Result authenticateDenied(String provider) {
		if (log.isDebugEnabled())
			log.debug("authenticateDenied() <-" + provider);
		flash(FLASH_ERROR_KEY,
				"You need to accept the connection in order to login");
		return redirect(routes.App.index());
	}

}
