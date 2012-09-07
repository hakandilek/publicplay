package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@SuppressWarnings("serial")
public class Comment extends Model {

	@Id
	public Long key;

    @ManyToOne
    @JoinColumn(name="postKey", nullable=false)
	public Post post;

	@Required
	public String content;

	public static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);

	public static List<Comment> all() {
		return find.all();
	}

	public static void create(Comment comment) {
		comment.save();
	}

	public static void delete(Long key) {
		find.ref(key).delete();
	}

	public static Comment get(Long key) {
		return find.ref(key);
	}

	public static void update(Long key, Comment comment) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [key=").append(key).append(", post=")
				.append(post == null ? null : post.getKey()).append(", content=").append(content).append("]");
		return builder.toString();
	}

}
