package socialauth.service;

import java.util.HashMap;
import java.util.Map;

import org.brickred.socialauth.Profile;

import play.api.Plugin;
import socialauth.core.SocialUser;
import socialauth.core.UserId;

public class SocialUserService implements Plugin {

	private static SocialUserService instance;// plugin instance

	private Map<UserId, SocialUser> users = new HashMap<UserId, SocialUser>();

	public SocialUserService() {
	}

	public void save(Profile profile) {
		UserId userId = new UserId(profile);
		SocialUser user = new SocialUser(profile);
		users.put(userId, user);
	}

	public SocialUser find(UserId userId) {
		return users.get(userId);
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
