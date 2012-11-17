package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import models.Comment;
import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.S3File;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.CommentDeletePermission;
import security.CommentEditPermission;
import security.PostDeletePermission;
import security.PostEditPermission;
import security.RestrictApproved;
import security.RestrictCombine;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import utils.HttpUtils;
import views.html.index;
import views.html.postForm;
import views.html.postShow;
import views.html.rate;
import views.html.helper.H;

import com.avaje.ebean.Page;

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
		final Set<Long> votedPostKeys = user == null ? new TreeSet<Long>() : user.getVotedPostKeys();
		
		Page<Post> topDay = Post.topDayPage();
		Page<Post> topWeek = Post.topWeekPage();
		Page<Post> topAll = Post.topAllPage();

		Page<Post> pg = Post.page(page, POSTS_PER_PAGE);
		return ok(index.render(pg, topDay, topWeek, topAll, user, votedPostKeys));
	}
	
	@Secure
	@RestrictApproved
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());

		return ok(postForm.render(null, form, user));
	}

	@Secure
	@RestrictApproved
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
			
			S3File image = HttpUtils.uploadFile(request(), "image");
			if (log.isDebugEnabled())
				log.debug("image : " + image);
			
			if (image != null) {
				image.parent = "Post";
				S3File.create(image);
				if (log.isDebugEnabled())
					log.debug("image : " + image);
				post.setImage(image);
			}
			Post.create(post);
			if (log.isDebugEnabled())
				log.debug("entity created: " + post);
			
			return redirect(routes.HomeController.index());
		}
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
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
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
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
		
		User user = HttpUtils.loginUser(ctx());
		final Set<Long> votedPostKeys = user == null ? new TreeSet<Long>() : user.getVotedPostKeys();

		final Page<Comment> pg = Comment.page(postKey, page, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, user, pg, votedPostKeys));
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostDeletePermission.class)
	@RestrictApproved
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
	@RestrictApproved
	public static Result createComment(Long postKey, String title) {
		if (log.isDebugEnabled())
			log.debug("createComment() <-" + postKey);
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			final Set<Long> votedPostKeys = user == null ? new TreeSet<Long>() : user.getVotedPostKeys();

			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, null, filledForm, user, pg, votedPostKeys));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			comment.setCreatedBy(user);
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);

			Comment.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created");
			
			final Long key = post.getKey();
			return redirect(routes.PostController.show(key, title, 0));
		}
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result editCommentForm(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("editCommentForm() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		Comment comment = Comment.get(commentKey);
		if (log.isDebugEnabled())
			log.debug("comment : " + comment);
		
		User user = HttpUtils.loginUser(ctx());
		final Set<Long> votedPostKeys = user == null ? new TreeSet<Long>() : user.getVotedPostKeys();

		Form<Comment> form = commentForm.fill(comment);
		final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, commentKey, form, user, pg, votedPostKeys));
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result updateComment(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("updateComment() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Comment> filledForm = commentForm.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			final Set<Long> votedPostKeys = user == null ? new TreeSet<Long>() : user.getVotedPostKeys();

			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, commentKey, filledForm, user, pg, votedPostKeys));
		} else {
			Comment comment = filledForm.get();
			comment.setUpdatedBy(user);
			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			Comment.update(commentKey, comment);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			final Long key = post.getKey();
			final String title = H.sanitize(post.getTitle());
			return redirect(routes.PostController.show(key, title, 0));
		}
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentDeletePermission.class)
	@RestrictApproved
	public static Result deleteComment(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("deleteComment() <-");
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		Comment.remove(commentKey);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		final Long key = post.getKey();
		final String title = H.sanitize(post.getTitle());
		return redirect(routes.PostController.show(key, title, 0));
	}

	/**
	 * rating is done via ajax, therefore return simply the eventual rate sum
	 */
	@Secure
	@RestrictApproved
	public static Result rateUp(Long key) {
		if (log.isDebugEnabled())
			log.debug("rateUp <-" + key);
		return rate(key, 1);
	}

	@Secure
	@RestrictApproved
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
			
			user.resetPostKeyCache();
			
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
