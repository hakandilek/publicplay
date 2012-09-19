package controllers;

import models.Comment;
import models.Post;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import utils.HttpUtils;

public class PostController extends Controller {

	private static ALogger log = Logger.of(PostController.class);

	static Form<Post> postForm = form(Post.class);

	static Form<Comment> commentForm = form(Comment.class);

	@SocialAware
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());

		return ok(views.html.postForm.render(null, postForm, user));
	}

	@Secure
	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		User user = HttpUtils.loginUser(ctx());

		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(views.html.postForm.render(null, filledForm, user));
		} else {
			Post post = filledForm.get();
			post.createdBy = user;
			Post.create(post);
			if (log.isDebugEnabled())
				log.debug("entity created");
			
			return redirect(routes.HomeController.index());
		}
	}

	@Secure
	public static Result editForm(Long key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);
		
		Post post = Post.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Post> form = postForm.fill(post);
		return ok(views.html.postForm.render(key, form, user));
	}

	@Secure
	public static Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Post> filledForm = postForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(views.html.postForm.render(key, filledForm, user));
		} else {
			Post post = filledForm.get();
			post.updatedBy = user;
			if (log.isDebugEnabled())
				log.debug("post : " + post);
			Post.update(key, post);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			return redirect(routes.HomeController.index());
		}
	}

	@SocialAware
	public static Result show(Long key, String title) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + key);
		
		Post post = Post.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		User user = HttpUtils.loginUser(ctx());

		return ok(views.html.postShow.render(post, null, commentForm, selfUrl, user));
	}

	@Secure
	public static Result delete(Long key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);
		
		Post.remove(key);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		return redirect(routes.HomeController.index());
	}

	//Comment stuff
	@Secure
	public static Result createComment(Long postKey, String title) {
		if (log.isDebugEnabled())
			log.debug("createComment() <-" + postKey);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(views.html.postShow.render(post, null, filledForm, selfUrl, user));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			comment.createdBy = user;
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);

			Comment.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created");
			
			return ok(views.html.postShow.render(post, null, commentForm, selfUrl, user));
		}
	}

	@Secure
	public static Result editCommentForm(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("editCommentForm() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		Comment comment = Comment.get(commentKey);
		if (log.isDebugEnabled())
			log.debug("comment : " + comment);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		User user = HttpUtils.loginUser(ctx());

		Form<Comment> form = commentForm.fill(comment);
		return ok(views.html.postShow.render(post, commentKey, form, selfUrl, user));
	}

	@Secure
	public static Result updateComment(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("updateComment() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(views.html.postShow.render(post, commentKey, filledForm, selfUrl, user));
		} else {
			Comment comment = filledForm.get();
			comment.updatedBy = user;
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			Comment.update(commentKey, comment);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			return ok(views.html.postShow.render(post, commentKey, commentForm, selfUrl, user));
		}
	}

	@Secure
	public static Result deleteComment(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("deleteComment() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		Comment.remove(commentKey);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		User user = HttpUtils.loginUser(ctx());

		return ok(views.html.postShow.render(post, null, commentForm, selfUrl, user));
	}

	/**
	 * rating is done via ajax, therefore return simply the eventual rate sum
	 */
	@Secure
	public static Result rateUp(Long postKey) {
		if (log.isDebugEnabled())
			log.debug("rateUp <-" + postKey);
		return rate(postKey, 1);
	}

	@Secure
	public static Result rateDown(Long postKey) {
		if (log.isDebugEnabled())
			log.debug("rateDown <-" + postKey);
		return rate(postKey, -1);
	}

	public static Result rate(Long postKey, int rate) {
		if (log.isDebugEnabled())
			log.debug("rate <-" + postKey);

		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		if (user != null) {
			//TODO:save/update rate
			return ok(views.html.rate.render(rate));
		} else {
			if (log.isDebugEnabled())
				log.debug("no user");
			return TODO;
		}
	}

}