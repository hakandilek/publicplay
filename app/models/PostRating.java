package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@SuppressWarnings("serial")
public class PostRating extends Model {

	@ManyToMany
	@JoinColumn(name = "postKey", nullable = false)
	public Post post;

	@Required
	public long value;

	@Basic
	public Date createdOn;

	@Basic
	public Date updatedOn;

	public static Finder<Long, PostRating> find = new Finder<Long, PostRating>(
			Long.class, PostRating.class);

	public static List<PostRating> all() {
		return find.all();
	}

	public static void create(PostRating comment) {
		Date now = new Date();
		comment.setCreatedOn(now);
		comment.setUpdatedOn(now);
		comment.save();
	}

	public static void remove(Long key) {
		find.ref(key).delete();
	}

	public static PostRating get(Long key) {
		return find.ref(key);
	}

	public static void update(Long key, PostRating comment) {
		Date now = new Date();
		comment.setUpdatedOn(now);
		comment.update(key);
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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

}
