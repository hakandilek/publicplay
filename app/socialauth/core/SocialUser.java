package socialauth.core;

import org.brickred.socialauth.Profile;

public class SocialUser {

	private UserId userId;
	private Profile profile;

	public SocialUser(UserId userId, Profile profile) {
		super();
		this.userId = userId;
		this.profile = profile;
	}

	public SocialUser(Profile profile) {
		this(new UserId(profile), profile);
	}

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
