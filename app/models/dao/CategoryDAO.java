package models.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.Category;
import models.Post;
import play.Logger;
import play.Logger.ALogger;
import play.utils.dao.CachedDAO;
import play.utils.dao.EntityNotFoundException;

@Singleton
public class CategoryDAO extends CachedDAO<String, Category> {

	private static ALogger log = Logger.of(CategoryDAO.class);

	PostDAO postDAO;

	@Inject
	public CategoryDAO(PostDAO postDAO) {
		super(String.class, Category.class);
		this.postDAO = postDAO;
	}

	public Map<String, String> options() {
		Map<String, String> m = new TreeMap<String, String>();
		final List<Category> all = all();
		for (Category cat: all) {
			m.put(cat.getName(), cat.getName());
		}
		return m;
	}
	
	public void update(String key, Category newCat) {
		Category oldCat = get(key);
		final Set<Post> posts = oldCat.getPosts();
		try {
			remove(key);
			create(newCat);
			for (Post post : posts) {
				post.setCategory(newCat);
				postDAO.update(post.getKey(), post);
			}
			find.put(newCat.getName(), newCat);
		} catch (EntityNotFoundException e) {
			log.error("entity not found:" +key, e);
		}
	}


}
