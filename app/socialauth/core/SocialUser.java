package socialauth.core;

import java.util.Calendar;
import java.util.Date;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.util.BirthDate;

public class SocialUser {

	private final String userKey;
	private final Profile profile;

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

	public Date getBirthDate() {
		final BirthDate dob = getProfile().getDob();
		final Calendar cal = Calendar.getInstance();
		cal.set(dob.getYear(), dob.getMonth(), dob.getDay(), 0, 0, 0);
		return cal.getTime();
	}
}
