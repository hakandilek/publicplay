package models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Version;


import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

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
	
	@Version
	public int revision;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    public Set<Comment> comments;

	public static Finder<Long, Post> find = new Finder<Long, Post>(Long.class, Post.class);

	public static List<Post> all() {
		return find.all();
	}

	public static void create(Post post) {
		Date now = new Date();
		post.setCreatedOn(now);
		post.setUpdatedOn(now);
		post.save();
	}

	public static void delete(Long key) {
		find.ref(key).delete();
	}

	public static Post get(Long key) {
		return find.ref(key);
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

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [key=").append(key).append(", title=")
				.append(title).append(", content=").append(content).append("]");
		return builder.toString();
	}

}
