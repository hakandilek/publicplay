package models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
@SuppressWarnings("serial")
public class Post extends Model {

	@Id
	public Long key;

	@Required
	public String title;

	@Required
	public String content;

    @Basic
	public Date createdOn;

    @Basic
	public Date updatedOn;

    //TODO:many-to-many
	public PostRating rating;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    public Set<Comment> comments;
    
    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
	public User createdBy;

    @ManyToOne
    @JoinColumn(name="updated_by", nullable=true)
	public User updatedBy;

	public static Finder<Long, Post> find = new Finder<Long, Post>(Long.class, Post.class);

	public static List<Post> all() {
		return find.all();
	}

	/**
	 * Return a page of posts
	 * 
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 * @param sortBy
	 *            property used for sorting
	 * @param order
	 *            Sort order (either or asc or desc)
	 * @param filter
	 *            Filter applied on the name column
	 */
	public static Page<Post> page(int page, int pageSize, String sortBy,
			String order, String filter) {
		return find.where().ilike("title", "%" + filter + "%")
				.orderBy(sortBy + " " + order).findPagingList(pageSize)
				.getPage(page);
	}
   
	public static void create(Post post) {
		Date now = new Date();
		post.setCreatedOn(now);
		post.setUpdatedOn(now);
		post.save();
	}

	public static void remove(Long key) {
		find.ref(key).delete();
	}

	public static Post get(Long key) {
		return find.byId(key);
	}

	public static void update(Long key, Post post) {
		Date now = new Date();
		post.setUpdatedOn(now);
		post.update(key);
	}
	
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
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
	
	public PostRating getRating() {
		return rating;
	}

	public void setRating(PostRating rating) {
		this.rating = rating;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [key=").append(key).append(", title=")
				.append(title).append(", content=").append(content).append("]");
		return builder.toString();
	}

}
