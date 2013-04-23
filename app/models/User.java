package models;

import java.util.ArrayList;
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

import play.db.ebean.Model;
import play.utils.dao.TimestampModel;
import security.Approvable;
import security.EntityPermission;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.validation.Email;
import com.restfb.Facebook;

import static com.restfb.util.DateUtils.toDateFromShortFormat;
import static com.restfb.util.StringUtils.isBlank;

@Entity
@Table(name="TBL_USER")
@SuppressWarnings("serial")
public class User extends Model implements Subject, Approvable, TimestampModel<String> {

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
	private int reputationValue;

	@Basic
	private Date lastLogin;

	@Basic
	@Facebook("first_name")
	private String firstName;

	@Basic
	@Facebook("last_name")
	private String lastName;

	@Basic
	@Facebook("birthday") //birthday as string, used for facebook mapping
	private String birthday;

	@Basic
	@Email
	@Facebook("email")
	private String email;

	@Basic
	private String country;

	@Basic
	@Facebook("gender")
	private String gender;

	@Basic
	@Facebook("location")
	private String location;

	@Basic
	private String profileImageURL;

	@Basic
	private String provider;
	
	@Basic
	private String accessToken;

	@Basic
	private Date accessExpires;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
	private Set<Action> actions = new HashSet<Action>();
	
    @Enumerated(value=EnumType.STRING)
    private Status status = Status.NEW;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    private Set<Post> posts = new HashSet<Post>();
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="createdBy")
    private Set<Comment> comments = new HashSet<Comment>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "TBL_USER_SECURITY_ROLE", joinColumns = @JoinColumn(name = "user_key"), inverseJoinColumns = @JoinColumn(name = "security_role_key"))
    private List<SecurityRole> securityRoles = new ArrayList<SecurityRole>();

	public static String getKey(String provider, String id) {
		return provider + "::" + id;
	}
	
	public void setKey(String provider, String id) {
		key = getKey(provider, id);
		this.provider = provider;
		this.originalKey = id;
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

	public String getIdentifier() {
		return key;
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

	public Set<Action> getActions() {
		return actions;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAccessExpires() {
		return accessExpires;
	}

	public void setAccessExpires(Date accessExpires) {
		this.accessExpires = accessExpires;
	}

	public String getBirthday() {
		return birthday;
	}

	public Date getBirthdate() {
		if (isBlank(birthday) || birthday.split("/").length < 2)
			return null;
		return toDateFromShortFormat(birthday);
	}

	public void merge(User facebookUser) {
		if (facebookUser == null)
			return;
		firstName = facebookUser.getFirstName();
		lastName = facebookUser.getLastName();
		birthday = facebookUser.getBirthday();
		email = facebookUser.getEmail();
		gender = facebookUser.getGender();
		location = facebookUser.getLocation();
	}

	public int getReputationValue() {
		return reputationValue;
	}

	public void setReputationValue(int reputation) {
		this.reputationValue = reputation;
	}
}