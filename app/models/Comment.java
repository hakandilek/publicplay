package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

@Entity
@SuppressWarnings("serial")
public class Comment extends Model {

	@Id
	public Long key;

	@Required
	public String content;
	
    @Basic
	public Date createdOn;

    @Basic
	public Date updatedOn;
	
    @ManyToOne
    @JoinColumn(name="postKey", nullable=false)
	public Post post;

    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
	public User createdBy;

    @ManyToOne
    @JoinColumn(name="updated_by", nullable=true)
	public User updatedBy;

	public static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);

	/**
	 * Return a page of comments
	 * 
	 * @param postKey
	 *            post key
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 */
	public static Page<Comment> page(Long postKey, int page, int pageSize) {
		return find.where().eq("postKey", postKey).orderBy("createdOn desc")
				.findPagingList(pageSize).getPage(page);
	}
   
	public static void create(Comment comment) {
		Date now = new Date();
		comment.setCreatedOn(now);
		comment.setUpdatedOn(now);
		comment.save();
	}

	public static void remove(Long key) {
		find.ref(key).delete();
	}

	public static Comment get(Long key) {
		return find.byId(key);
	}

	public static void update(Long key, Comment comment) {
		Date now = new Date();
		comment.setUpdatedOn(now);
		comment.update(key);
	}
	
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		builder.append("Comment [key=").append(key).append(", post=")
				.append(post == null ? null : post.getKey()).append(", content=").append(content).append("]");
		return builder.toString();
	}

}
