package controllers;
import static play.data.Form.*;

import static controllers.HttpUtils.*;
import static models.ActionType.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.inject.Inject;

import models.Category;
import models.Comment;
import models.ContentStatus;
import models.Post;
import models.ReputationType;
import models.S3File;
import models.User;
import models.dao.CategoryDAO;
import models.dao.CommentDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import models.dao.S3FileDAO;
import models.dao.UserFollowDAO;

import org.springframework.util.StringUtils;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Result;
import play.utils.crud.DynamicTemplateController;
import play.utils.dao.EntityNotFoundException;
import reputation.ReputationContext;
import views.html.index;
import views.html.postForm;
import views.html.postNotFound;
import views.html.postRemoved;
import views.html.postShow;

import com.avaje.ebean.Page;
public class PostController extends DynamicTemplateController implements
		Constants {

	private static ALogger log = Logger.of(PostController.class);

	private Form<Post> form = form(Post.class);
	
	private Form<Comment> commentForm = form(Comment.class);

	private CommentDAO commentDAO;

	private PostDAO postDAO;

	private PostRatingDAO postRatingDAO;

	private CategoryDAO categoryDAO;

	private S3FileDAO s3FileDAO;

	private UserFollowDAO userFollowDAO;

	private ActionHandler actionHandler;

	private ReputationHandler reputationHandler;

	@Inject
	public PostController(PostDAO postDAO, CommentDAO commentDAO,
			PostRatingDAO postRatingDAO, CategoryDAO categoryDAO,
			S3FileDAO s3FileDAO, UserFollowDAO userFollowDAO,
			ActionHandler actionHandler, ReputationHandler reputationHandler) {
		this.postDAO = postDAO;
		this.commentDAO = commentDAO;
		this.postRatingDAO = postRatingDAO;
		this.categoryDAO = categoryDAO;
		this.s3FileDAO = s3FileDAO;
		this.userFollowDAO = userFollowDAO;
		this.actionHandler = actionHandler;
		this.reputationHandler = reputationHandler;
	}

	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 */
	public Result list(int page, String categoryName) {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		if (log.isDebugEnabled())
			log.debug("page : " + page);

		User user = HttpUtils.loginUser();
		if (user == null)
			return notFound("no user logged in");

		Page<Post> pg = null;
		if (StringUtils.hasLength(categoryName)) {
			Category category = categoryDAO.get(categoryName);
			if (category != null)
				pg = postDAO.pageInCategory(category, page, POSTS_PER_PAGE);
		}

		if (pg == null) {
			pg = postDAO.page(page, POSTS_PER_PAGE);
		}
		if (log.isDebugEnabled())
			log.debug("pg : " + pg);

		Boolean following = false;
		List<Category> categoryList = categoryDAO.all();
		Page<Post> topDay = postDAO.topDayPage();
		Page<Post> topWeek = postDAO.topWeekPage();
		Page<Post> topAll = postDAO.topAllPage();

		return ok(index.render(pg, categoryName, categoryList, topDay, topWeek,
				topAll,getUpVotes(user), getDownVotes(user),following));
	}
	
	public Result listFollowing(int page) {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		User user = HttpUtils.loginUser();
		if (user == null)
			return notFound("no user logged in");

		List<String> followingUserKeys = userFollowDAO.getAllFollowingsKeys(user);
		Page<Post> pg=null;
		if (followingUserKeys != null && followingUserKeys.size() > 0) {
			pg = postDAO.getPostsCreatedBy(followingUserKeys, page,
					POSTS_PER_PAGE);
		}
		
		if (log.isDebugEnabled())
			log.debug("pg : " + pg);

		Boolean following=true;
		List<Category> categoryList = categoryDAO.all();
		Page<Post> topDay = postDAO.topDayPage();
		Page<Post> topWeek = postDAO.topWeekPage();
		Page<Post> topAll = postDAO.topAllPage();


		return ok(index.render(pg, null, categoryList, topDay, topWeek,
				topAll, getUpVotes(user), getDownVotes(user),following));
	}

	
	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");

		S3File image = null;
		return ok(postForm.render(null, form, image, categories()));
	}

	public Result create() {
		try {
			if (log.isDebugEnabled())
				log.debug("create() <-");

			User user = HttpUtils.loginUser();
			if (user == null)
				return notFound("no user logged in");

			Form<Post> filledForm = form.bindFromRequest();
			if (log.isDebugEnabled())
				log.debug("filledForm : " + filledForm);

			UUID imageKey = filledForm.get().getImageKey();
			if (log.isDebugEnabled())
				log.debug("imageKey : " + imageKey);

			S3File image = null;
			if (imageKey != null)
				image = s3FileDAO.get(imageKey);
			if (log.isDebugEnabled())
				log.debug("image : " + image);

			if (filledForm.hasErrors() || user == null) {
				if (log.isDebugEnabled())
					log.debug("form.data : " + form.data());
				if (log.isDebugEnabled())
					log.debug("validation errors occured");

				Map<String, List<ValidationError>> errors = filledForm.errors();
				if (log.isDebugEnabled())
					log.debug("errors : " + errors);

				return badRequest(postForm.render(null, filledForm, image,
						categories()));
			} else {
				Post post = filledForm.get();
				post.setCreatedBy(user);
				post.setCreatorIp(request().remoteAddress());
				post.setStatus(ContentStatus.NEW);

				if (image != null) {
					post.setImage(image);
				}

				postDAO.create(post);
				
				actionHandler.perform(user, post, CREATE_POST);
				reputationHandler.evaluate(new ReputationContext(post), ReputationType.CREATE_POST);
				
				if (log.isDebugEnabled())
					log.debug("entity created: " + post);

				return redirect(routes.App.index());
			}
		} catch (Exception e) {
			log.error("error occured", e);
			e.printStackTrace();
			return null;
		}
	}

	public Result editForm(Long key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);

		Post post = postDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		S3File image = post.getImage();
		if (log.isDebugEnabled())
			log.debug("image : " + image);

		Form<Post> frm = form.fill(post);
		return ok(postForm.render(key, frm, image, categories()));
	}

	public Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);

		User user = HttpUtils.loginUser();
		if (user == null)
			return notFound("no user logged in");

		Post original = postDAO.get(key);
		if (original == null)
			return notFound("post not found");
		
		Form<Post> fromDb = form.fill(original);
		if (log.isDebugEnabled())
			log.debug("fromDb : " + fromDb);

		Form<Post> filledForm = fromDb.bindFromRequest();
		if (log.isDebugEnabled())
			log.debug("filledForm : " + filledForm);

		UUID imageKey = filledForm.get().getImageKey();
		if (log.isDebugEnabled())
			log.debug("imageKey : " + imageKey);
		S3File image = null;
		if (imageKey != null){
			image = s3FileDAO.get(imageKey);
		}
		if (log.isDebugEnabled())
			log.debug("image : " + image);

		if (log.isDebugEnabled())
			log.debug("filledForm : " + filledForm);
		// ignore missing images
		filledForm.errors().remove("image");

		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			Map<String, List<ValidationError>> errors = filledForm.errors();
			if (log.isDebugEnabled())
				log.debug("errors : " + errors);

			return badRequest(postForm.render(key, filledForm, image,
					categories()));
		} else {
			Post postData = postDAO.get(key);
			Post post = filledForm.get();
			post.setRating(postData.getRating());
			post.setCreatedBy(postData.getCreatedBy());
			post.setCreatedOn(postData.getCreatedOn());
			post.setCreatorIp(postData.getCreatorIp());
			post.setUpdatedBy(user);
			post.setModifierIp(request().remoteAddress());
			post.setStatus(ContentStatus.UPDATED);
			post.setKey(postData.getKey());
			post.setRevision(postData.getRevision());
			if (image != null) {
				post.setImage(image);
			}

			if (log.isDebugEnabled())
				log.debug("post : " + post);
			postDAO.update(post);
			if (log.isDebugEnabled())
				log.debug("entity updated");

			return redirect(routes.App.index());
		}
	}

	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 */
	public Result show(Long postKey, String title, int page) {
		if (log.isDebugEnabled())
			log.debug("show() <-" + postKey);

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		if (post == null) {
			return notFound(postNotFound.render(postKey));
		}
		
		User user = loginUser(ctx());
		if (!isAdmin(user) && post.getStatus() == ContentStatus.REMOVED) {
			return notFound(postRemoved.render(postKey));
		}


		final Page<Comment> pg = commentDAO.page(postKey, page,
				COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, pg, getUpVotes(user),
				getDownVotes(user)));
	}

	public List<String> categories() {
		List<Category> all = categoryDAO.all();
		List<String> list = new ArrayList<String>();
		for (Category category : all) {
			list.add(category.getName());
		}
		return list;
	}

	public Result delete(Long key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);

		try {
			postDAO.remove(key);
			if (log.isDebugEnabled())
				log.debug("entity deleted");
		} catch (EntityNotFoundException e) {
			if (log.isDebugEnabled())
				log.debug("entity not found for key:" + key);
			return notFound(postNotFound.render(key));
		}

		return redirect(routes.App.index());
	}
	
	private Set<Long> getDownVotes(User user) {
		Set<Long> downVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(user);
		return downVotes;
	}

	private Set<Long> getUpVotes(User user) {
		Set<Long> upVotes = user == null ? new TreeSet<Long>() : postRatingDAO
				.getUpVotedPostKeys(user);
		return upVotes;
	}


}