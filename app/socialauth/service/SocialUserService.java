package socialauth.service;

import java.util.HashMap;
import java.util.Map;

import org.brickred.socialauth.Profile;

import play.Application;
import play.Logger;
import play.Logger.ALogger;
import play.api.Plugin;
import socialauth.core.SocialUser;

public class SocialUserService implements Plugin {
	private static ALogger log = Logger.of(SocialUserService.class);

	private static SocialUserService instance;// plugin instance

	private Map<String, SocialUser> users = new HashMap<String, SocialUser>();

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
		
		SocialUser user = new SocialUser(profile);
		users.put(userKey, user);
		
		if (log.isDebugEnabled())
			log.debug("saved user profile.");
	}

	public SocialUser find(String userKey) {
		if (log.isDebugEnabled())
			log.debug("find() <-");
		if (log.isDebugEnabled())
			log.debug("userKey : " + userKey);
		
		SocialUser user = users.get(userKey);
		
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
