package controllers.crud;

import static play.data.Form.*;

import javax.inject.Inject;

import models.Category;
import models.dao.CategoryDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class CategoryCRUDController extends CRUDController<String, Category> {

	@Inject
	public CategoryCRUDController(CategoryDAO dao) {
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
