package controllers.crud;

import javax.inject.Inject;

import models.Category;
import models.dao.CategoryDAO;
import play.Logger;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.crud.Parameters;
import controllers.routes;

public class CategoryController extends CRUDController<String, Category> {

	@Inject
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
		return routes.Admin.categoryList();
	}

	@Override
	public Result newForm() {
		// TODO Auto-generated method stub
		return super.newForm();
	}

	@Override
	protected Result ok(String temp, Parameters params) {
		if (Logger.isDebugEnabled())
			Logger.debug("temp : " + temp);
		if (Logger.isDebugEnabled())
			Logger.debug("params : " + params);
		// TODO Auto-generated method stub
		return super.ok(temp, params);
	}

	
}
