package controllers;

import java.util.List;
import java.util.Map;
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
import play.data.validation.ValidationError;
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
		final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
		final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();
		
		Page<Post> topDay = Post.topDayPage();
		Page<Post> topWeek = Post.topWeekPage();
		Page<Post> topAll = Post.topAllPage();

		Page<Post> pg = Post.page(page, POSTS_PER_PAGE);
		return ok(index.render(pg, topDay, topWeek, topAll, user, upVotes, downVotes));
	}
	
	@Secure
	@RestrictApproved
	public static Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = null;
		return ok(postForm.render(null, form, user, image));
	}

	@Secure
	@RestrictApproved
	public static Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = HttpUtils.uploadFile(request(), "image");
		if (log.isDebugEnabled())
			log.debug("image : " + image);
		
		Form<Post> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("form.data : " + form.data());
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			Map<String, List<ValidationError>> errors = filledForm.errors();
			if (log.isDebugEnabled())
				log.debug("errors : " + errors);
			
			return badRequest(postForm.render(null, filledForm, user, image));
		} else {
			Post post = filledForm.get();
			post.setCreatedBy(user);
			post.setCreatorIp(request().remoteAddress());
			
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
		
		S3File image = post.getImage();
		if (log.isDebugEnabled())
			log.debug("image : " + image);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Post> frm = form.fill(post);
		return ok(postForm.render(key, frm, user, image));
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public static Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = HttpUtils.uploadFile(request(), "image");
		if (log.isDebugEnabled())
			log.debug("image : " + image);
		
		Form<Post> filledForm = form.bindFromRequest();
		if (log.isDebugEnabled())
			log.debug("filledForm : " + filledForm);
		//ignore missing images
		filledForm.errors().remove("image");
		
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			Map<String, List<ValidationError>> errors = filledForm.errors();
			if (log.isDebugEnabled())
				log.debug("errors : " + errors);
			
			return badRequest(postForm.render(key, filledForm, user, image));
		} else {
			Post postData = Post.get(key);
			Post post = filledForm.get();
			post.setRating(postData.getRating());
			post.setCreatedBy(postData.getCreatedBy());
			post.setCreatedOn(postData.getCreatedOn());
			post.setCreatorIp(postData.getCreatorIp());
			post.setUpdatedBy(user);
			post.setModifierIp(request().remoteAddress());

			if (image != null) {
				image.parent = "Post";
				S3File.create(image);
				if (log.isDebugEnabled())
					log.debug("image : " + image);
				post.setImage(image);
			}
			
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
		final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
		final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();

		final Page<Comment> pg = Comment.page(postKey, page, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, user, pg, upVotes, downVotes));
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
			
			final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
			final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();

			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, null, filledForm, user, pg, upVotes, downVotes));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			comment.setCreatedBy(user);
			comment.setCreatorIp(request().remoteAddress());

			if (log.isDebugEnabled())
				log.debug("comment : " + comment);

			Comment.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created : " + comment);
			
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
		final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
		final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();

		Form<Comment> form = commentForm.fill(comment);
		final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, commentKey, form, user, pg, upVotes, downVotes));
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
			
			final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
			final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();

			final Page<Comment> pg = Comment.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, commentKey, filledForm, user, pg, upVotes, downVotes));
		} else {
			final Comment commentData = Comment.get(commentKey);
			Comment comment = filledForm.get();
			comment.setCreatedBy(commentData.getCreatedBy());
			comment.setCreatedOn(commentData.getCreatedOn());
			comment.setCreatorIp(commentData.getCreatorIp());
			comment.setUpdatedBy(user);
			comment.setModifierIp(request().remoteAddress());

			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			Comment.update(commentKey, comment);
			if (log.isDebugEnabled())
				log.debug("entity updated");
			
			final Long key = post.getKey();
			final String title = H.sanitizeURL(post.getTitle());
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
		final String title = H.sanitizeURL(post.getTitle());
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
			
			user.resetVotedPostKeyCache();
			
			if (log.isDebugEnabled())
				log.debug("updating post : " + post);
			Post.update(postKey, post);
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
