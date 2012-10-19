package controllers;

import be.objectify.deadbolt.actions.Restrict;
import play.mvc.Result;
import socialauth.core.Secure;
import models.Comment;
import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;
import crud.controllers.CRUD;
import crud.controllers.CRUDController;

public class AdminController extends CRUDController {

	private final static AdminController instance = new AdminController();

	public AdminController() {
		super(new CRUD[] { 
				new CRUD<Long, Post>(Post.class, Post.find),
				new CRUD<Long, Comment>(Comment.class, Comment.find),
				new CRUD<String, User>(User.class, User.find), 
				new CRUD<PostRatingPK, PostRating>(PostRating.class, PostRating.find), 
		});
	}

	@Secure
	@Restrict("admin")
	public static Result index() {
		return instance.doIndex();
	}

	@Secure
	@Restrict("admin")
	public static Result list(String model, int page) {
		return instance.doList(model, page);
	}

	@Secure
	@Restrict("admin")
	public static Result newForm(String model) {
		return instance.doNewForm(model);
	}
	
	@Secure
	@Restrict("admin")
	public static Result create(String model) {
		return instance.doCreate(model);
	}
	
	@Secure
	@Restrict("admin")
	public static Result editForm(String model, String key) {
		return instance.doEditForm(model, key);
	}
	
	@Secure
	@Restrict("admin")
	public static Result update(String model, String key) {
		return instance.doUpdate(model, key);
	}
	
	@Secure
	@Restrict("admin")
	public static Result delete(String model, String key) {
		return instance.doDelete(model, key);
	}
	
	@Secure
	@Restrict("admin")
	public static Result show(String model, String key) {
		return instance.doShow(model, key);
	}
}
