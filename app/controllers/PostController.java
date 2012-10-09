package controllers;

import com.avaje.ebean.Page;

import models.Comment;
import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
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
	 */
	@SocialAware
	public static Result list(int page) {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		if (log.isDebugEnabled())
			log.debug("page : " + page);

		User user = HttpUtils.loginUser(ctx());
		Page<Post> topDay = Post.topDayPage();
		Page<Post> topWeek = Post.topWeekPage();
		Page<Post> topAll = Post.topAllPage();

		Page<Post> pg = Post.page(page, POSTS_PER_PAGE);
		return ok(index.render(pg, topDay, topWeek, topAll, user));
	}
	
	@Secure
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());

		return ok(postForm.render(null, form, user));
	}

	@Secure
	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		User user = HttpUtils.loginUser(ctx());

		Form<Post> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(postForm.render(null, filledForm, user));
		} else {
			Post post = filledForm.get();
			post.setCreatedBy(user);
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
		
		Form<Post> frm = form.fill(post);
		return ok(postForm.render(key, frm, user));
	}

	@Secure
	public static Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Post> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(postForm.render(key, filledForm, user));
		} else {
			Post postData = Post.get(key);
			Post post = filledForm.get();
			post.setRating(postData.getRating());
			post.setUpdatedBy(user);
			if (log.isDebugEnabled())
				log.debug("post : " + post);
			Post.update(key, post);
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
	 */
	@SocialAware
	public static Result show(Long postKey, String title, int page) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + postKey);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		String selfUrl = HttpUtils.selfURL();
		if (log.isDebugEnabled())
			log.debug("selfUrl : " + selfUrl);
		
		User user = HttpUtils.loginUser(ctx());

		final Page<Comment> pg = Comment.page(postKey, page, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, selfUrl, user, pg));
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
			
			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, null, filledForm, selfUrl, user, pg));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			comment.setCreatedBy(user);
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);

			Comment.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created");
			
			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return ok(postShow.render(post, null, commentForm, selfUrl, user, pg));
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
		final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, commentKey, form, selfUrl, user, pg));
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
			
			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, commentKey, filledForm, selfUrl, user, pg));
		} else {
			Comment comment = filledForm.get();
			comment.setUpdatedBy(user);
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			Comment.update(commentKey, comment);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return ok(postShow.render(post, commentKey, commentForm, selfUrl, user, pg));
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

		final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
		return ok(views.html.postShow.render(post, null, commentForm, selfUrl, user, pg));
	}

	/**
	 * rating is done via ajax, therefore return simply the eventual rate sum
	 */
	@Secure
	public static Result rateUp(Long key) {
		if (log.isDebugEnabled())
			log.debug("rateUp <-" + key);
		return rate(key, 1);
	}

	@Secure
	public static Result rateDown(Long key) {
		if (log.isDebugEnabled())
			log.debug("rateDown <-" + key);
		return rate(key, -1);
	}

	public static Result rate(Long postKey, int rating) {
		if (log.isDebugEnabled())
			log.debug("rate <-" + postKey);
		if (log.isDebugEnabled())
			log.debug("rating : " + rating);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		if (post != null && user != null) {
			//save/update rate
			if (post.getRating() == null)
				post.setRating(0);
			PostRating pr = PostRating.get(user, post);
			if (log.isDebugEnabled())
				log.debug("pr : " + pr);
			
			PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
			if (pr == null) {
				pr = new PostRating();
				pr.setValue(rating);
				pr.setKey(key);
				PostRating.create(pr);
				post.setRating(post.getRating() + rating);
			} else {
				int ratingBefore = pr.getValue();
				pr.setValue(rating);
				PostRating.update(key, pr);
				if (log.isDebugEnabled())
					log.debug("post.rating : " + post.getRating());
				if (log.isDebugEnabled())
					log.debug("pr.value    : " + ratingBefore);
				post.setRating(post.getRating() - ratingBefore + rating);
			}
			
			if (log.isDebugEnabled())
				log.debug("updating post : " + post);
			post.update(postKey);
			if (log.isDebugEnabled())
				log.debug("post : " + post);
			
			return ok(rate.render(post.getRating()));
		} else {
			if (log.isDebugEnabled())
				log.debug("no user");
			return TODO;
		}
	}

	public static Result rateShow(Long postKey) {
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		if (post != null) {
			return ok(rate.render(post.getRating()));
		}
		
		if (log.isDebugEnabled())
			log.debug("no post");
		return TODO;
	}
}
