package socialauth.service;

import java.util.HashMap;
import java.util.Map;

import org.brickred.socialauth.Profile;

import play.Application;
import play.api.Plugin;
import socialauth.core.SocialUser;

public class SocialUserService implements Plugin {

	private static SocialUserService instance;// plugin instance

	private Map<String, SocialUser> users = new HashMap<String, SocialUser>();

	public SocialUserService(Application app) {
	}

	public void save(String userKey, Profile profile) {
		SocialUser user = new SocialUser(profile);
		users.put(userKey, user);
	}

	public SocialUser find(String userKey) {
		return users.get(userKey);
	}

	@Override
	public boolean enabled() {
		return true;
	}

	@Override
	public void onStart() {
		instance = this;
	}

	@Override
	public void onStop() {
		instance = null;
	}

	public static SocialUserService getInstance() {
		return instance;
	}

}
