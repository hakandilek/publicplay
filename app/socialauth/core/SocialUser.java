package socialauth.core;

import java.util.Date;

public interface SocialUser {

	String getUserKey();

	Date getBirthdate();

	String getValidatedId();

	String getFirstName();

	String getLastName();

	String getEmail();

	String getCountry();

	String getGender();

	String getLocation();

	String getProfileImageURL();

	String getProviderId();

}
