package controllers;

import java.util.List;

import models.Category;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.Secure;
import views.html.categoryForm;
import views.html.categoryList;
import views.html.categoryShow;

public class CategoryController extends Controller implements Constants {

	private static ALogger log = Logger.of(CategoryController.class);

	static Form<Category> form = form(Category.class);

	/**
	 * Display list of categories
	 */
	@Secure
	public static Result list() {
		if (log.isDebugEnabled())
			log.debug("list <-");
		final List<Category> all = Category.all();
		return ok(categoryList.render(all));
	}
	
	@Secure
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		return ok(categoryForm.render(null, form));
	}

	@Secure
	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		Form<Category> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(categoryForm.render(null, filledForm));
		} else {
			Category category = filledForm.get();
			Category.create(category);
			if (log.isDebugEnabled())
				log.debug("entity created");
			
			return redirect(routes.CategoryController.list());
		}
	}

	@Secure
	public static Result editForm(String name) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + name);
		
		Category category = Category.get(name);
		if (log.isDebugEnabled())
			log.debug("category : " + category);
		
		Form<Category> frm = form.fill(category);
		return ok(categoryForm.render(name, frm));
	}

	@Secure
	public static Result update(String name) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + name);
		
		Form<Category> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(categoryForm.render(name, filledForm));
		} else {
			Category category = filledForm.get();
			if (log.isDebugEnabled())
				log.debug("category : " + category);
			Category.update(name, category);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			return redirect(routes.CategoryController.list());
		}
	}

	/**
	 * Display a category
	 */
	@Secure
	public static Result show(String name) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + name);
		
		Category category = Category.get(name);
		if (log.isDebugEnabled())
			log.debug("category : " + category);

		return ok(categoryShow.render(category));
	}

	@Secure
	public static Result delete(String name) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + name);
		
		Category.remove(name);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		return redirect(routes.CategoryController.list());
	}

}
