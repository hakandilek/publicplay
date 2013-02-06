package controllers.crud;

import javax.inject.Inject;

import models.Category;
import models.dao.CategoryDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class CategoryController extends CRUDController<String, Category> {

	@Inject
	public CategoryController(CategoryDAO dao) {
		super(dao, form(Category.class), String.class, Category.class, 20, "name");
	}

	@Override
	protected String templateForForm() {
		return "admin.categoryForm";
	}

	@Override
	protected String templateForList() {
		return "admin.categoryList";
	}

	@Override
	protected String templateForShow() {
		return "admin.categoryShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.categoryList(0);
	}

}
