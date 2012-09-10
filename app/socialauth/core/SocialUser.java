package socialauth.core;

import org.brickred.socialauth.Profile;

public class SocialUser {

	public final String userKey;
	public final Profile profile;

	public SocialUser(String userKey, Profile profile) {
		this.userKey = userKey;
		this.profile = profile;
	}

	public SocialUser(Profile profile) {
		this(profile.getProviderId() + "::" + profile.getValidatedId(), profile);
	}

	public String getUserKey() {
		return userKey;
	}

	public Profile getProfile() {
		return profile;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocialUser [").append(userKey)
				.append(" = ").append(profile).append("]");
		return builder.toString();
	}
}
