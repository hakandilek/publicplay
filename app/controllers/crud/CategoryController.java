package controllers.crud;

import models.Category;
import models.dao.CategoryDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class CategoryController extends CRUDController<String, Category> {

	public CategoryController(CategoryDAO dao) {
		super(dao, form(Category.class), String.class, Category.class);
	}

	@Override
	protected String templateForForm() {
		return "categoryForm";
	}

	@Override
	protected String templateForList() {
		return "categoryList";
	}

	@Override
	protected String templateForShow() {
		return "categoryShow";
	}

	@Override
	protected Call toIndex() {
		return crudIndex();
	}

	public static Call crudIndex() {
		// TODO Auto-generated method stub
		return null;
	}

}
