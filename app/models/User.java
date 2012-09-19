package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.util.BirthDate;

import play.db.ebean.Model;
import socialauth.core.SocialUser;

@Entity
@SuppressWarnings("serial")
public class User extends Model {

	@Id
	public String key;

	@Basic
	public Date createdOn;

	@Basic
	public Date updatedOn;

	@Version
	public int revision;

	@Basic
	public int loginCount;

	@Basic
	public Date lastLogin;

	@Basic
	public String firstName;

	@Basic
	public String lastName;

	@Basic
	public Date birthdate;

	@Basic
	public String email;

	@Basic
	public String country;

	@Basic
	public String gender;

	@Basic
	public String location;

	@Basic
	public String profileImageURL;

	@Basic
	public String providerId;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    public Set<Post> posts;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    public Set<Comment> comments;
	

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);

	public User(SocialUser socialUser) {
		key = socialUser.getUserKey();
		final Profile profile = socialUser.getProfile();
		firstName = profile.getFirstName();
		lastName = profile.getLastName();
		birthdate = socialUser.getBirthDate();
		email = profile.getEmail();
		country = profile.getCountry();
		gender = profile.getGender();
		location = profile.getLocation();
		profileImageURL = profile.getProfileImageURL();
		providerId = profile.getProviderId();
	}

	public SocialUser toSocialUser() {
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		if (birthdate != null) {
			final BirthDate dob = new BirthDate();
			final Calendar cal = Calendar.getInstance();
			cal.setTime(birthdate);
			dob.setDay(cal.get(Calendar.DAY_OF_MONTH) + 1);
			dob.setMonth(cal.get(Calendar.MONTH) + 1);
			dob.setDay(cal.get(Calendar.YEAR) + 1900);
			profile.setDob(dob);
		}
		profile.setEmail(email);
		profile.setCountry(country);
		profile.setGender(gender);
		profile.setLocation(location);
		profile.setProfileImageURL(profileImageURL);
		profile.setProviderId(providerId);

		SocialUser su = new SocialUser(key, profile);
		return su;
	}

	public static List<User> all() {
		return find.all();
	}

	public static void create(User user) {
		Date now = new Date();
		user.setCreatedOn(now);
		user.setUpdatedOn(now);
		user.save();
	}

	public static void remove(String key) {
		find.ref(key).delete();
	}

	public static User get(String key) {
		return find.byId(key);
	}

	public static void update(String key, User user) {
		Date now = new Date();
		user.setUpdatedOn(now);
		user.update(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email;
	}

	public String getCountry() {
		return country;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public String getProviderId() {
		return providerId;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [key=").append(key).append(", firstName=")
				.append(getFirstName()).append(", lastName=").append(getLastName())
				.append(", email=").append(getEmail()).append("]");
		return builder.toString();
	}

}