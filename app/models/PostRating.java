package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="TBL_POST_RATING")
@SuppressWarnings("serial")
public class PostRating extends Model {

	@EmbeddedId
	private PostRatingPK key;
    
	@Required
	private int value;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;

	public static Finder<PostRatingPK, PostRating> find = new Finder<PostRatingPK, PostRating>(
			PostRatingPK.class, PostRating.class);

	public static PostRating get(User user, Post post) {
		PostRating rating = find.where().eq("user_key", user.getKey())
				.eq("post_key", post.getKey()).findUnique();
		return rating;
	}

	public static List<PostRating> get(User user) {
		List<PostRating> ratings = find.where().eq("user_key", user.getKey())
				.findList();
		return ratings;
	}

	public static PostRating get(String userKey, Long postKey) {
		PostRatingPK key = new PostRatingPK(userKey, postKey);
		return find.byId(key);
	}

	public static void create(PostRating rating) {
		Date now = new Date();
		rating.setCreatedOn(now);
		rating.setUpdatedOn(now);
		rating.save();
	}

	public static void update(PostRatingPK key, PostRating rating) {
		Date now = new Date();
		rating.setUpdatedOn(now);
		rating.update();
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
	
	public PostRatingPK getKey() {
		return key;
	}

	public void setKey(PostRatingPK key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostRating [key=").append(key).append(", value=")
				.append(value).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append("]");
		return builder.toString();
	}
	
	

}
