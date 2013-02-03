package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.util.BirthDate;

import play.db.ebean.Model;
import play.utils.cache.CachedFinder;
import play.utils.dao.TimestampModel;
import security.Approvable;
import security.EntityPermission;
import socialauth.core.SocialUser;
import be.objectify.deadbolt.models.Permission;
import be.objectify.deadbolt.models.Role;
import be.objectify.deadbolt.models.RoleHolder;

import com.avaje.ebean.annotation.EnumValue;

@Entity
@Table(name="TBL_USER")
@SuppressWarnings("serial")
public class User extends Model implements RoleHolder, Approvable, TimestampModel<String> {

	public enum Status {
		@EnumValue("N")
		NEW, 
		@EnumValue("A")
		APPROVED, 
		@EnumValue("S")
		SUSPENDED,
	}

	@Id
	private String key;

	@Basic
	private String originalKey;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;

	@Version
	private int revision;

	@Basic
	private int loginCount;

	@Basic
	private Date lastLogin;

	@Basic
	private String firstName;

	@Basic
	private String lastName;

	@Basic
	private Date birthdate;

	@Basic
	private String email;

	@Basic
	private String country;

	@Basic
	private String gender;

	@Basic
	private String location;

	@Basic
	private String profileImageURL;

	@Basic
	private String provider;
	
    @Enumerated(value=EnumType.STRING)
    private Status status = Status.NEW;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    private Set<Post> posts = new HashSet<Post>();
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    private Set<Comment> comments = new HashSet<Comment>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TBL_USER_SECURITY_ROLE", joinColumns = @JoinColumn(name = "user_key"), inverseJoinColumns = @JoinColumn(name = "security_role_key"))
    private List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();

	public static CachedFinder<String, User> find = new CachedFinder<String, User>(
			String.class, User.class);

	public User(SocialUser socialUser) {
		key = socialUser.getUserKey();
		final Profile profile = socialUser.getProfile();
		originalKey = profile.getValidatedId();
		firstName = profile.getFirstName();
		lastName = profile.getLastName();
		birthdate = socialUser.getBirthDate();
		email = profile.getEmail();
		country = profile.getCountry();
		gender = profile.getGender();
		location = profile.getLocation();
		profileImageURL = profile.getProfileImageURL();
		provider = profile.getProviderId();
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
			dob.setMonth(cal.get(Calendar.MONTH));
			dob.setDay(cal.get(Calendar.YEAR) + 1900);
			profile.setDob(dob);
		}
		profile.setEmail(email);
		profile.setCountry(country);
		profile.setGender(gender);
		profile.setLocation(location);
		profile.setProfileImageURL(profileImageURL);
		profile.setProviderId(provider);
		profile.setValidatedId(originalKey);

		SocialUser su = new SocialUser(key, profile);
		return su;
	}

	public List<? extends Permission> getPermissions() {
		Set<Post> posts = getPosts();
		Set<Comment> comments = getComments();
		ArrayList<Permission> permissions = new ArrayList<Permission>();
		for (Post post : posts) {
			permissions.add(new EntityPermission(Post.class, "delete", post.getKey()));
			permissions.add(new EntityPermission(Post.class, "edit", post.getKey()));
		}
		for (Comment comment : comments) {
			permissions.add(new EntityPermission(Comment.class, "delete", comment.getKey()));
			permissions.add(new EntityPermission(Comment.class, "edit", comment.getKey()));
		}
		return permissions;
	}

	public List<? extends Role> getRoles() {
		return new ArrayList<SecurityRole>(securityRoles);
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
		return provider;
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
		this.provider = providerId;
	}

	public String getOriginalKey() {
		return originalKey;
	}

	public void setOriginalKey(String originalKey) {
		this.originalKey = originalKey;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public List<SecurityRole> getSecurityRoles() {
		return securityRoles;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isNew() {
		return Status.NEW == this.status;
	}
	
	public boolean isApproved() {
		return Status.APPROVED == this.status;
	}
	
	public boolean isSuspended() {
		return Status.SUSPENDED == this.status;
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