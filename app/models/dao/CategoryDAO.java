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
		if (log.isDebugEnabled())
			log.debug("update() <-");
		if (log.isDebugEnabled())
			log.debug("key : " + key);
		if (log.isDebugEnabled())
			log.debug("newCat : " + newCat);
		
		Category oldCat = get(key);
		final Set<Post> posts = oldCat.getPosts();
		try {
			
			//create new category
			if (log.isDebugEnabled())
				log.debug("create newCat : " + newCat);
			create(newCat);
			
			for (Post post : posts) {
				//assign posts to the new category
				
				Long postKey = post.getKey();
				Post p = postDAO.get(postKey);
				p.setCategory(newCat);
				
				if (log.isDebugEnabled())
					log.debug("update p : " + p);
				postDAO.update(postKey, p);
			}
			
			//remove old category
			if (log.isDebugEnabled())
				log.debug("remove key : " + key);
			remove(key);
			
			//update cache
			String newKey = newCat.getKey();
			find.clean(newKey);
			newCat = get(newKey);
			if (log.isDebugEnabled())
				log.debug("update cache for newCat : " + newCat);
			find.put(newKey, newCat);
		} catch (EntityNotFoundException e) {
			log.error("entity not found:" +key, e);
		}
		
		if (log.isDebugEnabled())
			log.debug(" update finished.");
	}

	public void cacheClean(String cacheKey) {
		find.clean(cacheKey);
	}


}
