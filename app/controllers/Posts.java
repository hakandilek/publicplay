package controllers;

import java.util.List;

import models.Post;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Posts extends Controller {

	static Form<Post> postForm = form(Post.class);

	public static Result index() {
		List<Post> list = Post.all();
		return ok(views.html.posts.render(list));
	}

	public static Result newForm() {
		return ok(views.html.postsForm.render(postForm));
	}

	public static Result create() {
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postsForm.render(filledForm));
		} else {
			Post.create(filledForm.get());
			return redirect(routes.Posts.index());
		}
	}

	public static Result editForm(long key) {
		Post post = Post.get(key);
		Form<Post> form = postForm.fill(post);
		return ok(views.html.postsForm.render(form));
	}

	public static Result update(long key) {
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postsForm.render(filledForm));
		} else {
			Post.update(key, filledForm.get());
			return redirect(routes.Posts.index());
		}
	}

	public static Result show(long key) {
		Post post = Post.get(key);
		return ok(views.html.postsShow.render(post));
	}

	public static Result delete(long key) {
		Post.delete(key);
		return redirect(routes.Posts.index());
	}

}