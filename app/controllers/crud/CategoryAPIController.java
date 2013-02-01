package controllers.crud;

import javax.inject.Inject;

import models.Category;
import models.dao.CategoryDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class CategoryAPIController extends APIController<String, Category> {


	@Inject
	public CategoryAPIController(CategoryDAO categoryDAO) {
		super(categoryDAO);
	}

	@Override
	public Result create() {
		/* TODO:
		Result check = checkRequired("url");
		if (check != null) {
			return check;
		}

		String url = jsonText("url");
		try {
			new URL(url );
		} catch (MalformedURLException e) {
			return badRequest(toJson(ImmutableMap.of(
					"status", "error", 
					"message", e.getMessage())));
		}
		
		Category m = new Category();
		m.setStatus(Category.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
