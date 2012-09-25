package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@SuppressWarnings("serial")
public class PostRating extends Model {

	@EmbeddedId
    public PostRatingPK key;
    
	@Required
	public int value;

	@Basic
	public Date createdOn;

	@Basic
	public Date updatedOn;

	public static Finder<PostRatingPK, PostRating> find = new Finder<PostRatingPK, PostRating>(
			PostRatingPK.class, PostRating.class);

	public static PostRating get(User user, Post post) {
		PostRating rating = find.where().eq("user_key", user.key)
				.eq("post_key", post.key).findUnique();
		return rating;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostRating [key=").append(key).append(", value=")
				.append(value).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append("]");
		return builder.toString();
	}
	
	

}
