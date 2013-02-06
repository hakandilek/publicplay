package controllers.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.Category;
import models.Comment;
import models.Post;
import models.S3File;
import models.User;
import models.dao.CategoryDAO;
import models.dao.CommentDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import models.dao.S3FileDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import views.html.index;
import views.html.postForm;
import views.html.postShow;

import com.avaje.ebean.Page;

import controllers.Constants;
import controllers.HttpUtils;
import controllers.routes;

public class PostController extends CRUDController<Long, Post> implements
		Constants {

	private static ALogger log = Logger.of(PostController.class);

	private Form<Comment> commentForm = form(Comment.class);

	private CommentDAO commentDAO;

	private PostDAO postDAO;

	private PostRatingDAO postRatingDAO;

	private HttpUtils httpUtils;

	private CategoryDAO categoryDAO;

	private S3FileDAO s3FileDAO;

	@Inject
	public PostController(PostDAO postDAO, CommentDAO commentDAO, PostRatingDAO postRatingDAO, CategoryDAO categoryDAO, S3FileDAO s3FileDAO, HttpUtils httpUtils) {
		super(postDAO, form(Post.class), Long.class, Post.class, 20, "updatedOn desc");
		this.postDAO = postDAO;
		this.commentDAO = commentDAO;
		this.postRatingDAO = postRatingDAO;
		this.categoryDAO = categoryDAO;
		this.s3FileDAO = s3FileDAO;
		this.httpUtils = httpUtils;
	}

	@Override
	protected String templateForForm() {
		return "postForm";
	}

	@Override
	protected String templateForList() {
		return "postList";
	}

	@Override
	protected String templateForShow() {
		return "postShow";
	}

	@Override
	protected Call toIndex() {
		return routes.App.index();
	}

	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 */
	public Result list(int page) {
		if (log.isDebugEnabled())
			log.debug("index() <-");

		if (log.isDebugEnabled())
			log.debug("page : " + page);

		User user = httpUtils.loginUser(ctx());
		Set<Long> upVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(user);
		Set<Long> downVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(user);

		Page<Post> topDay = postDAO.topDayPage();
		Page<Post> topWeek = postDAO.topWeekPage();
		Page<Post> topAll = postDAO.topAllPage();

		Page<Post> pg = postDAO.page(page, POSTS_PER_PAGE);
		if (Logger.isDebugEnabled())
			Logger.debug("pg : " + pg);
		return ok(index.render(pg, topDay, topWeek, topAll, user, upVotes,
				downVotes));
	}

	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");

		User user = httpUtils.loginUser(ctx());

		S3File image = null;
		return ok(postForm.render(null, getForm(), user, image, categories()));
	}

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");

		User user = httpUtils.loginUser(ctx());

		S3File image = httpUtils.uploadFile(request(), "image");
		if (log.isDebugEnabled())
			log.debug("image : " + image);

		Form<Post> filledForm = getForm().bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("form.data : " + getForm().data());
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			Map<String, List<ValidationError>> errors = filledForm.errors();
			if (log.isDebugEnabled())
				log.debug("errors : " + errors);

			return badRequest(postForm.render(null, filledForm, user, image, categories()));
		} else {
			Post post = filledForm.get();
			post.setCreatedBy(user);
			post.setCreatorIp(request().remoteAddress());

			if (image != null) {
				image.parent = "Post";
				s3FileDAO.create(image);
				if (log.isDebugEnabled())
					log.debug("image : " + image);
				post.setImage(image);
			}

			postDAO.create(post);
			if (log.isDebugEnabled())
				log.debug("entity created: " + post);

			return redirect(routes.App.index());
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

		User user = httpUtils.loginUser(ctx());

		Form<Post> frm = getForm().fill(post);
		return ok(postForm.render(key, frm, user, image, categories()));
	}

	public Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);

		User user = httpUtils.loginUser(ctx());

		S3File image = httpUtils.uploadFile(request(), "image");
		if (log.isDebugEnabled())
			log.debug("image : " + image);

		Form<Post> filledForm = getForm().bindFromRequest();
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

			return badRequest(postForm.render(key, filledForm, user, image, categories()));
		} else {
			Post postData = postDAO.get(key);
			Post post = filledForm.get();
			post.setRating(postData.getRating());
			post.setCreatedBy(postData.getCreatedBy());
			post.setCreatedOn(postData.getCreatedOn());
			post.setCreatorIp(postData.getCreatorIp());
			post.setUpdatedBy(user);
			post.setModifierIp(request().remoteAddress());

			if (image != null) {
				image.parent = "Post";
				s3FileDAO.create(image);
				if (log.isDebugEnabled())
					log.debug("image : " + image);
				post.setImage(image);
			}

			if (log.isDebugEnabled())
				log.debug("post : " + post);
			postDAO.update(key, post);
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

		User user = httpUtils.loginUser(ctx());
		Set<Long> upVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(user);
		Set<Long> downVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(user);

		final Page<Comment> pg = commentDAO.page(postKey, page,
				COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, user, pg, upVotes,
				downVotes));
	}

	public List<String> categories() {
		List<Category> all = categoryDAO.all();
		List<String> list = new ArrayList<String>();
		for (Category category : all) {
			list.add(category.getName());
		}
		return list;
	}

}