package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.utils.dao.BasicModel;

@Entity
@Table(name="TBL_CATEGORY")
@SuppressWarnings("serial")
public class Category extends Model implements BasicModel<String> {

	@Id
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private Set<Post> posts;


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
	public String getKey() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [").append(name).append("]");
		return builder.toString();
	}

}
