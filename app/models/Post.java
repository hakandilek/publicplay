package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Post extends Model {

	@Id
	public Long key;

	@Required
	public String title;

	@Required
	public String content;

	public static Finder<Long, Post> find = new Finder(Long.class, Post.class);

	public static List<Post> all() {
		return find.all();
	}

	public static void create(Post post) {
		post.save();
	}

	public static void delete(Long key) {
		find.ref(key).delete();
	}

	public static Post get(long key) {
		return find.ref(key);
	}

	public static void update(long key, Post post) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [key=").append(key).append(", title=")
				.append(title).append(", content=").append(content).append("]");
		return builder.toString();
	}

}
