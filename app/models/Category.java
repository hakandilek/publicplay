package models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.utils.cache.CachedFinder;

@Entity
@SuppressWarnings("serial")
public class Category extends Model {

	@Id
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private Set<Post> posts;

	public static CachedFinder<String, Category> find = new CachedFinder<String, Category>(
			String.class, Category.class);

	public static List<Category> all() {
		return find.all();
	}

	public static void create(Category c) {
		c.save();
		find.put(c.getName(), c);
	}

	public static void remove(String key) {
		find.ref(key).delete();
		find.clean(key);
	}

	public static Category get(String key) {
		return find.byId(key);
	}

	public static void update(String key, Category newCat) {
		Category oldCat = get(key);
		final Set<Post> posts = oldCat.getPosts();
		remove(key);
		create(newCat);
		for (Post post : posts) {
			post.setCategory(newCat);
			Post.update(post.getKey(), post);
		}
		find.put(newCat.getName(), newCat);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [").append(name).append("]");
		return builder.toString();
	}


}
