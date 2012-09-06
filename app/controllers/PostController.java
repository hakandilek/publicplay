package controllers;

import java.util.List;

import models.Post;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class PostController extends Controller {

	static Form<Post> postForm = form(Post.class);

	public static Result index() {
		List<Post> list = Post.all();
		return ok(views.html.postList.render(list));
	}

	public static Result newForm() {
		return ok(views.html.postForm.render(null, postForm));
	}

	public static Result create() {
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postForm.render(null, filledForm));
		} else {
			Post.create(filledForm.get());
			return redirect(routes.PostController.index());
		}
	}

	public static Result editForm(Long key) {
		Post post = Post.get(key);
		Form<Post> form = postForm.fill(post);
		return ok(views.html.postForm.render(key, form));
	}

	public static Result update(Long key) {
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postForm.render(key, filledForm));
		} else {
			Post.update(key, filledForm.get());
			return redirect(routes.PostController.index());
		}
	}

	public static Result show(Long key) {
		Post post = Post.get(key);
		return ok(views.html.postShow.render(post));
	}

	public static Result delete(Long key) {
		Post.delete(key);
		return redirect(routes.PostController.index());
	}

}