package controllers;

import java.util.List;

import models.Category;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import utils.HttpUtils;
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
	@RestrictApproved
	public static Result list() {
		if (log.isDebugEnabled())
			log.debug("list <-");
		final User user = HttpUtils.loginUser(ctx());
		final List<Category> all = Category.all();
		return ok(categoryList.render(all, user));
	}
	
	@Secure
	@RestrictApproved
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());
		return ok(categoryForm.render(null, form, user));
	}

	@Secure
	@RestrictApproved
	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		Form<Category> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			User user = HttpUtils.loginUser(ctx());
			return badRequest(categoryForm.render(null, filledForm, user));
		} else {
			Category category = filledForm.get();
			Category.create(category);
			if (log.isDebugEnabled())
				log.debug("entity created");
			
			return redirect(routes.CategoryController.list());
		}
	}

	@Secure
	@RestrictApproved
	public static Result editForm(String name) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + name);
		
		Category category = Category.get(name);
		if (log.isDebugEnabled())
			log.debug("category : " + category);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Category> frm = form.fill(category);
		return ok(categoryForm.render(name, frm, user));
	}

	@Secure
	@RestrictApproved
	public static Result update(String name) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + name);
		
		Form<Category> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			User user = HttpUtils.loginUser(ctx());
			return badRequest(categoryForm.render(name, filledForm, user));
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
	@RestrictApproved
	public static Result show(String name) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + name);
		
		Category category = Category.get(name);
		if (log.isDebugEnabled())
			log.debug("category : " + category);

		User user = HttpUtils.loginUser(ctx());
		
		return ok(categoryShow.render(category, user));
	}

	@Secure
	@RestrictApproved
	public static Result delete(String name) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + name);
		
		Category.remove(name);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		return redirect(routes.CategoryController.list());
	}

}
