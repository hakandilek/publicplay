package socialauth.service;

import java.util.Date;

import models.User;

import org.brickred.socialauth.Profile;

import play.Application;
import play.Logger;
import play.Logger.ALogger;
import play.api.Plugin;
import socialauth.core.SocialUser;

public class SocialUserService implements Plugin {
	
	private static ALogger log = Logger.of(SocialUserService.class);

	private static SocialUserService instance;// plugin instance

	public SocialUserService(Application app) {
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
		
		User user = User.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		if (user != null) {
			user.loginCount++;
			user.setLastLogin(new Date());
			user.update();
		} else {
			SocialUser socialUser = new SocialUser(profile);
			user = new User(socialUser);
			User.create(user);
		}
		
		if (log.isDebugEnabled())
			log.debug("saved user profile.");
	}

	public SocialUser find(String userKey) {
		if (log.isDebugEnabled())
			log.debug("find() <-");
		if (log.isDebugEnabled())
			log.debug("userKey : " + userKey);
		
		final User user = User.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		SocialUser socialUser = null;
		if (user != null) {
			socialUser = user.toSocialUser();
			if (log.isDebugEnabled())
				log.debug("socialUser : " + socialUser);
		}
		return socialUser;
	}

	@Override
	public boolean enabled() {
		return true;
	}

	@Override
	public void onStart() {
		instance = this;
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " started.");

	}

	@Override
	public void onStop() {
		instance = null;
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " stopped.");
	}

	public static SocialUserService getInstance() {
		return instance;
	}

}
