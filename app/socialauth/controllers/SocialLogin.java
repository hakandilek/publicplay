package socialauth.controllers;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

import play.Logger;
import play.Logger.ALogger;
import play.api.mvc.Call;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.Secure;
import socialauth.core.SocialUser;
import socialauth.core.SocialUtils;
import socialauth.service.SocialUserService;
import views.html.userLogin;
import views.html.userInfo;

public class SocialLogin extends Controller {
	private static ALogger log = Logger.of(SocialLogin.class);

	/** session key for authenticated user */
	public static final String USER_KEY = "socialUser";

	public static final String ORIGINAL_URL = "originalURL";

	private static SocialAuthConfig authConfig;

	private static SocialAuthManager createAuthManager(String provider) {
		if (log.isDebugEnabled())
			log.debug("Creating AuthManager ...");

		try {
			// Create an instance of SocialAuthManager and set config
			SocialAuthConfig config = getAuthConfig();
			if (config == null)
				return null;
			SocialAuthManager authManager = new SocialAuthManager();
			authManager.setSocialAuthConfig(config);
			String successUrl = authSuccessURL(provider);
			authManager.getAuthenticationUrl(provider, successUrl);
			return authManager;
		} catch (Exception e) {
			// problem occured
			log.error("problem occured creating auth manager", e);
			return null;
		}
	}

	private static SocialAuthConfig getAuthConfig() {
		if (authConfig == null) {
			// Create an instance of SocialAuthConfgi object
			Properties properties = new Properties();
			ClassLoader loader = SocialAuthConfig.class.getClassLoader();
			try {
				InputStream in = loader
						.getResourceAsStream("socialauth.properties");
				properties.load(in);
				String proxyHost = System.getProperty("http.proxyHost");
				String proxyPort = System.getProperty("http.proxyPort");
				if (proxyHost != null)
					properties.setProperty("proxy.host", proxyHost);
				if (proxyPort != null)
					properties.setProperty("proxy.port", proxyPort);

				// load configuration. By default load the configuration from
				// socialauth.properties.
				// You can also pass input stream, properties object or properties
				// file name.
				authConfig = new SocialAuthConfig();
				authConfig.load(properties);
			} catch (Exception e) {
				log.error("error occured creating auth config", e);
				return null;
			}

		}
		return authConfig;
	}

	public static Result login() {
		if (log.isDebugEnabled())
			log.debug("login() <-");

		return ok(userLogin.render());
	}

	public static Result logout() {
		if (log.isDebugEnabled())
			log.debug("logout() <-");

		// TODO: log user logout into DB
		session().remove(USER_KEY);
		session().remove(ORIGINAL_URL);
		return redirect(controllers.routes.HomeController.index());
	}

	private static String authSuccessURL(String provider) {
		// URL of YOUR application which will be called after authentication
		final Call successCall = routes.SocialLogin
				.authenticateDone(provider);
		String successUrl = "http://" + request().host()
				+ successCall.url();
		return successUrl;
	}
	
	public static Result authenticate(String provider) {
		if (log.isDebugEnabled())
			log.debug("authenticate() with provider = " + provider);

		String originalURL = session(ORIGINAL_URL);
		if (log.isDebugEnabled())
			log.debug("originalURL = " + originalURL);

		if (SocialUtils.emptyOrNull(originalURL)) {
			if (log.isDebugEnabled())
				log.debug("setting referer...");

			String referer = request().getHeader("referer");
			if (log.isDebugEnabled())
				log.debug("No original URL setting referer = " + referer);
			if (referer != null) {
				session(ORIGINAL_URL, referer);
				originalURL = referer;
			}
		}

		try {
			// get Provider URL to which you should redirect for authentication.
			// id can
			// have values "facebook", "twitter", "yahoo" etc. or the OpenID URL
			SocialAuthManager manager = createAuthManager(provider);
			String successUrl = authSuccessURL(provider);
			String url = manager.getAuthenticationUrl(provider, successUrl);
			if (log.isDebugEnabled())
				log.debug("redirecting to url : " + url);
			return redirect(url);
		} catch (Exception e) {
			log.error("error occured authenticating, redirecting to originalURL=" + originalURL, e);
			return redirect(originalURL);
		}
	}

	public static Result authenticateDone(String provider) {
		if (log.isDebugEnabled())
			log.debug("authenticateDone() <-" + provider);

		try {
			// TODO use NIO here
			
			SocialAuthManager manager = createAuthManager(provider);
			if (log.isDebugEnabled())
				log.debug("manager : " + manager);
			
			Map<String, String> parameters = SocialUtils.parameters(request());
			if (log.isDebugEnabled())
				log.debug("parameters : " + parameters);
			// authenticate
			AuthProvider auth = manager.connect(parameters);
			// get profile
			Profile profile = auth.getUserProfile();
			if (log.isDebugEnabled())
				log.debug("profile : " + profile);

			final SocialUser user = new SocialUser(profile);
			if (log.isDebugEnabled())
				log.debug("user : " + user);

			final String userKey = user.getUserKey();
			if (log.isDebugEnabled())
				log.debug("userKey : " + userKey);

			if (userKey != null)
				session(USER_KEY, userKey);

			// save profile information
			SocialUserService userService = SocialUserService.getInstance();
			if (log.isDebugEnabled())
				log.debug("userService : " + userService);
			if (userService != null)
				userService.save(userKey, profile);

		} catch (Exception e) {
			log.trace("error occured during authentication. redirecting back", e);
			e.printStackTrace();
		}

		String originalURL = session(ORIGINAL_URL);
		if (log.isDebugEnabled())
			log.debug("originalURL : " + originalURL);

		if (!SocialUtils.emptyOrNull(originalURL)) {
			session().remove(ORIGINAL_URL);
			if (log.isDebugEnabled())
				log.debug("redirecting to originalURL : " + originalURL);

			return redirect(originalURL);
		}
		if (log.isDebugEnabled())
			log.debug("redirecting back to home");
		return redirect(controllers.routes.HomeController.index());
	}

	@Secure
	public static Result info() {
		final SocialUser user = (SocialUser) ctx().args
				.get(SocialLogin.USER_KEY);
		log.info("user info = " + user);
		return ok(userInfo.render(user));
	}

}
