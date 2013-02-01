package models.dao;

import models.Category;
import play.utils.dao.CachedDAO;

public class CategoryDAO extends CachedDAO<String, Category> {

	public CategoryDAO() {
		super(String.class, Category.class);
	}

}
