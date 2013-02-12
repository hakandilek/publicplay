package plugins;

import java.util.Date;

import models.User;
import models.dao.UserDAO;

import org.brickred.socialauth.Profile;

import play.Application;
import play.Logger;
import play.Logger.ALogger;
import play.api.Plugin;

public class SocialUserPlugin implements Plugin {
	
	private static ALogger log = Logger.of(SocialUserPlugin.class);

	private static SocialUserPlugin instance;// plugin instance

	private UserDAO userDAO;

	public SocialUserPlugin(Application app) {
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " created.");
	}

	public void save(String userKey, Profile profile) {
		if (log.isDebugEnabled())
			log.debug("save() <-");
		if (log.isDebugEnabled())
			log.debug("userKey : " + userKey);
		if (log.isDebugEnabled())
			log.debug("profile : " + profile);
		
		User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLogin(new Date());
			user.update();
		} else {
			user = new User(profile);
			userDAO.create(user);
		}
		
		if (log.isDebugEnabled())
			log.debug("saved user profile.");
	}

	public User find(String userKey) {
		if (log.isDebugEnabled())
			log.debug("find() <-");
		if (log.isDebugEnabled())
			log.debug("userKey : " + userKey);
		
		final User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

	@Override
	public boolean enabled() {
		return true;
	}

	@Override
	public void onStart() {
		instance = this;
		userDAO = GuicePlugin.getInstance().getInstance(UserDAO.class);

		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " started.");

	}

	@Override
	public void onStop() {
		instance = null;
		userDAO = null;
		
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " stopped.");
	}

	public static SocialUserPlugin getInstance() {
		return instance;
	}

}
