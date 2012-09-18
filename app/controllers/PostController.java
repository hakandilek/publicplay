package controllers;

import com.avaje.ebean.Page;

import models.Comment;
import models.Post;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.controllers.SocialLogin;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import socialauth.core.SocialUser;
import utils.HttpUtils;
import views.html.index;
import views.html.postForm;
import views.html.postShow;
import views.html.rate;

public class PostController extends Controller implements Constants {

	private static ALogger log = Logger.of(PostController.class);

	static Form<Post> form = form(Post.class);

	static Form<Comment> commentForm = form(Comment.class);

	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on computer names
	 */
	@SocialAware
	public static Result list(int page, String sortBy, String order,
			String filter) {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		if (log.isDebugEnabled())
			log.debug("page : " + page);
		if (log.isDebugEnabled())
			log.debug("sortBy : " + sortBy);
		if (log.isDebugEnabled())
			log.debug("order : " + order);
		if (log.isDebugEnabled())
			log.debug("filter : " + filter);

		SocialUser user = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		return ok(index.render(Post.page(page, POSTS_PER_PAGE, sortBy, order, filter),
				sortBy, order, filter, user));
	}
	
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		return ok(postForm.render(null, form));
	}

	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		Form<Post> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(postForm.render(null, filledForm));
		} else {
			Post.create(filledForm.get());
			if (log.isDebugEnabled())
				log.debug("entity created");
			
			return redirect(routes.HomeController.index());
		}
	}

	public static Result editForm(Long key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);
		
		Post post = Post.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		Form<Post> frm = form.fill(post);
		return ok(postForm.render(key, frm));
	}

	public static Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);
		
		Form<Post> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(postForm.render(key, filledForm));
		} else {
			Post.update(key, filledForm.get());
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			return redirect(routes.HomeController.index());
		}
	}

	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on computer names
	 */
	public static Result show(Long postKey, String title, int page, String sortBy, String order,
			String filter) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + postKey);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		final Page<Comment> pg = Comment.page(page, COMMENTS_PER_PAGE, sortBy, order, filter);
		return ok(postShow.render(post, null, commentForm, selfUrl, pg, sortBy,
				order, filter));
	}

	public static Result delete(Long key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);
		
		Post.remove(key);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		return redirect(routes.HomeController.index());
	}

	//Comment stuff
	public static Result createComment(Long postKey, String title) {
		if (log.isDebugEnabled())
			log.debug("createComment() <-" + postKey);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
			return badRequest(postShow.render(post, null, commentForm, selfUrl, pg, "createdOn", "desc", ""));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			
			Comment.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created");
			
			final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
			return ok(postShow.render(post, null, commentForm, selfUrl, pg, "createdOn", "desc", ""));
		}
	}

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
		
		Form<Comment> form = commentForm.fill(comment);
		final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
		return ok(postShow.render(post, commentKey, commentForm, selfUrl, pg, "createdOn", "desc", ""));
	}

	public static Result updateComment(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("updateComment() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
			return badRequest(postShow.render(post, commentKey, commentForm, selfUrl, pg, "createdOn", "desc", ""));
		} else {
			Comment.update(commentKey, filledForm.get());
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
			return ok(postShow.render(post, commentKey, commentForm, selfUrl, pg, "createdOn", "desc", ""));
		}
	}

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
		
		final Page<Comment> pg = Comment.page(0, COMMENTS_PER_PAGE, "createdOn", "desc", "");
		return ok(postShow.render(post, null, commentForm, selfUrl, pg, "createdOn", "desc", ""));
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

	public static Result rate(Long postKey, int rating) {
		if (log.isDebugEnabled())
			log.debug("rate <-" + postKey);

		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		final SocialUser socialUser = (SocialUser) ctx().args.get(SocialLogin.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("socialUser : " + socialUser);
		User user = null;
		if (socialUser != null)
			user = User.get(socialUser.getUserKey());
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		if (user != null) {
			//TODO:save/update rate
			return ok(rate.render(rating));
		} else {
			if (log.isDebugEnabled())
				log.debug("no user");
			return TODO;
		}
	}

}