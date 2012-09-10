package socialauth.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import socialauth.service.SocialUserService;
import views.html.login;

public class SocialLogin extends Controller {

	private static SocialAuthManager authManager;

	public static SocialAuthManager getAuthManager() {
		if (authManager == null) {
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
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SocialAuthConfig config = new SocialAuthConfig();

			try {
				// load configuration. By default load the configuration from
				// oauth_consumer.properties.
				// You can also pass input stream, properties object or
				// properties file
				// name.
				config.load(properties);

				// Create an instance of SocialAuthManager and set config
				authManager = new SocialAuthManager();
				authManager.setSocialAuthConfig(config);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return authManager;
	}

	public static Result login() {
		return ok(login.render());
	}

	public static Result logout() {
		return TODO;
	}

	public static Result authenticate(String provider) {

		try {
			// URL of YOUR application which will be called after authentication
			String successUrl = "http://" + request().host()
					+ routes.SocialLogin.authenticateDone(provider).url();

			// get Provider URL to which you should redirect for authentication.
			// id can have values "facebook", "twitter", "yahoo" etc. or the
			// OpenID
			// URL
			SocialAuthManager manager = getAuthManager();
			String url = manager.getAuthenticationUrl(provider, successUrl);

			return redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
			return TODO;
		}
	}

	public static Result authenticateDone(String provider) {
		SocialAuthManager manager = getAuthManager();
		Map<String, String> parameters = parameters(request());
		try {
			// TODO use NIO here
			// authenticate
			AuthProvider auth = manager.connect(parameters);
			// get profile
			Profile p = auth.getUserProfile();
			// save profile information
			SocialUserService userService = SocialUserService.getInstance();
			if (userService != null)
				userService.save(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redirect(controllers.routes.HomeController.index());
	}

	private static Map<String, String> parameters(Request request) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> queryString = request.queryString();
		Set<String> keySet = queryString.keySet();
		for (String key : keySet) {
			String[] strings = queryString.get(key);
			for (String value : strings) {
				if (value != null)
					params.put(key, value);
			}
		}
		return params;
	}

	public static Result info() {
		return TODO;
	}

}
