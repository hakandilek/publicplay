package controllers.crud;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.Comment;
import models.Post;
import models.S3File;
import models.User;
import models.dao.PostDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import utils.HttpUtils;
import views.html.index;
import views.html.postForm;
import views.html.postShow;

import com.avaje.ebean.Page;

import controllers.Constants;
import controllers.routes;

public class PostController extends CRUDController<Long, Post> implements Constants {

	private static ALogger log = Logger.of(PostController.class);

	private Form<Comment> commentForm = form(Comment.class);

	@Inject
	public PostController(PostDAO dao) {
		super(dao, form(Post.class), Long.class, Post.class);
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
		return crudIndex();
	}

	public static Call crudIndex() {
		return routes.Admin.postList();
	}

	
	/**
	 * Display the paginated list of posts.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 */
	public  Result list(int page) {
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
	
	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = null;
		return ok(postForm.render(null, getForm(), user, image));
	}

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = HttpUtils.uploadFile(request(), "image");
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
			
			return redirect(routes.App.index());
		}
	}

	public Result editForm(Long key) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);
		
		Post post = Post.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		S3File image = post.getImage();
		if (log.isDebugEnabled())
			log.debug("image : " + image);
		
		User user = HttpUtils.loginUser(ctx());
		
		Form<Post> frm = getForm().fill(post);
		return ok(postForm.render(key, frm, user, image));
	}

	public Result update(Long key) {
		if (log.isDebugEnabled())
			log.debug("update() <-" + key);
		
		User user = HttpUtils.loginUser(ctx());

		S3File image = HttpUtils.uploadFile(request(), "image");
		if (log.isDebugEnabled())
			log.debug("image : " + image);
		
		Form<Post> filledForm = getForm().bindFromRequest();
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
		
		Post post = Post.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		
		User user = HttpUtils.loginUser(ctx());
		final Set<Long> upVotes = user == null ? new TreeSet<Long>() : user.getUpVotedPostKeys();
		final Set<Long> downVotes = user == null ? new TreeSet<Long>() : user.getDownVotedPostKeys();

		final Page<Comment> pg = Comment.page(postKey, page, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, null, commentForm, user, pg, upVotes, downVotes));
	}

	public Result delete(Long key) {
		if (log.isDebugEnabled())
			log.debug("delete() <-" + key);
		
		Post.remove(key);
		if (log.isDebugEnabled())
			log.debug("entity deleted");
		
		return redirect(routes.App.index());
	}


}
