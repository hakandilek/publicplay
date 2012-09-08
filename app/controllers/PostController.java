package controllers;

import java.util.List;

import models.Comment;
import models.Post;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class PostController extends Controller {

	static Form<Post> postForm = form(Post.class);

	static Form<Comment> commentForm = form(Comment.class);

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
		return ok(views.html.postShow.render(post, null, commentForm));
	}

	public static Result delete(Long key) {
		Post.delete(key);
		return redirect(routes.PostController.index());
	}

	//Comment stuff

	public static Result createComment(Long postKey) {
		Post post = Post.get(postKey);
		Form<Comment> filledForm = commentForm.bindFromRequest();
		System.out.println("form:" + filledForm);
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postShow.render(post, null, filledForm));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			Comment.create(comment);
			return ok(views.html.postShow.render(post, null, commentForm));
		}
	}

	public static Result editCommentForm(Long postKey, Long commentKey) {
		Post post = Post.get(postKey);
		Comment comment = Comment.get(commentKey);
		Form<Comment> form = commentForm.fill(comment);
		return ok(views.html.postShow.render(post, commentKey, form));
	}

	public static Result updateComment(Long postKey, Long commentKey) {
		Post post = Post.get(postKey);
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.postShow.render(post, commentKey, filledForm));
		} else {
			Comment.update(commentKey, filledForm.get());
			return ok(views.html.postShow.render(post, commentKey, commentForm));
		}
	}

	public static Result deleteComment(Long postKey, Long commentKey) {
		Post post = Post.get(postKey);
		Comment.delete(commentKey);
		return ok(views.html.postShow.render(post, null, commentForm));
	}

}