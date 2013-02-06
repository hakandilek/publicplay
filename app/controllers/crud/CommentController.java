package controllers.crud;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.Comment;
import models.Post;
import models.User;
import models.dao.CommentDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.dao.EntityNotFoundException;
import views.html.postShow;
import views.html.helper.H;

import com.avaje.ebean.Page;

import controllers.Constants;
import controllers.HttpUtils;
import controllers.routes;

public class CommentController extends CRUDController<Long, Comment> implements Constants {

	private CommentDAO commentDAO;
	private PostDAO postDAO;
	private PostRatingDAO postRatingDAO;
	private HttpUtils httpUtils;

	@Inject
	public CommentController(PostDAO postDAO, CommentDAO dao, PostRatingDAO postRatingDAO, HttpUtils httpUtils) {
		super(dao, form(Comment.class), Long.class, Comment.class);
		this.postDAO = postDAO;
		this.commentDAO = dao;
		this.postRatingDAO = postRatingDAO;
		this.httpUtils = httpUtils;
	}

	@Override
	protected String templateForForm() {
		return "getForm()";
	}

	@Override
	protected String templateForList() {
		return "commentList";
	}

	@Override
	protected String templateForShow() {
		return "commentShow";
	}

	@Override
	protected Call toIndex() {
		return routes.App.index();
	}

	public Result create(Long postKey, String title) {
		if (log.isDebugEnabled())
			log.debug("createComment() <-" + postKey);

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		User user = httpUtils.loginUser(ctx());

		Form<Comment> filledForm = getForm().bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			Set<Long> upVotes = user == null ? new TreeSet<Long>()
					: postRatingDAO.getUpVotedPostKeys(user);
			Set<Long> downVotes = user == null ? new TreeSet<Long>()
					: postRatingDAO.getDownVotedPostKeys(user);

			Page<Comment> pg = commentDAO
					.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, null, filledForm, user, pg,
					upVotes, downVotes));
		} else {
			Comment comment = filledForm.get();
			comment.setPost(post);
			comment.setCreatedBy(user);
			comment.setCreatorIp(request().remoteAddress());

			if (log.isDebugEnabled())
				log.debug("comment : " + comment);

			commentDAO.create(comment);
			if (log.isDebugEnabled())
				log.debug("comment created : " + comment);

			final Long key = post.getKey();
			return redirect(routes.App.postShow(key, title, 0));
		}
	}

	public Result editForm(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("editCommentForm() <-");

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		Comment comment = commentDAO.get(commentKey);
		if (log.isDebugEnabled())
			log.debug("comment : " + comment);

		User user = httpUtils.loginUser(ctx());
		Set<Long> upVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(user);
		Set<Long> downVotes = user == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(user);

		Form<Comment> form = getForm().fill(comment);
		Page<Comment> pg = commentDAO.page(postKey, 0, COMMENTS_PER_PAGE);
		return ok(postShow.render(post, commentKey, form, user, pg, upVotes,
				downVotes));
	}

	public Result update(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("updateComment() <-");

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		User user = httpUtils.loginUser(ctx());

		Form<Comment> filledForm = getForm().bindFromRequest();
		if (filledForm.hasErrors() || user == null) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			Set<Long> upVotes = user == null ? new TreeSet<Long>()
					: postRatingDAO.getUpVotedPostKeys(user);
			Set<Long> downVotes = user == null ? new TreeSet<Long>()
					: postRatingDAO.getDownVotedPostKeys(user);

			Page<Comment> pg = commentDAO
					.page(postKey, 0, COMMENTS_PER_PAGE);
			return badRequest(postShow.render(post, commentKey, filledForm,
					user, pg, upVotes, downVotes));
		} else {
			final Comment commentData = commentDAO.get(commentKey);
			Comment comment = filledForm.get();
			comment.setCreatedBy(commentData.getCreatedBy());
			comment.setCreatedOn(commentData.getCreatedOn());
			comment.setCreatorIp(commentData.getCreatorIp());
			comment.setUpdatedBy(user);
			comment.setModifierIp(request().remoteAddress());

			if (log.isDebugEnabled())
				log.debug("comment : " + comment);
			commentDAO.update(commentKey, comment);
			if (log.isDebugEnabled())
				log.debug("entity updated");

			final Long key = post.getKey();
			final String title = H.sanitizeURL(post.getTitle());
			return redirect(routes.App.postShow(key, title, 0));
		}
	}

	public Result delete(Long postKey, Long commentKey) {
		if (log.isDebugEnabled())
			log.debug("deleteComment() <-");

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		try {
			commentDAO.remove(commentKey);
		} catch (EntityNotFoundException e) {
			return notFound("not found:" + postKey);
		}
		if (log.isDebugEnabled())
			log.debug("entity deleted");

		final Long key = post.getKey();
		final String title = H.sanitizeURL(post.getTitle());
		return redirect(routes.App.postShow(key, title, 0));
	}

}
