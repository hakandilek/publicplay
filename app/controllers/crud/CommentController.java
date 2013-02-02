package controllers.crud;

import javax.inject.Inject;

import controllers.routes;
import models.Comment;
import models.dao.CommentDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class CommentController extends CRUDController<Long, Comment> {

	@Inject
	public CommentController(CommentDAO dao) {
		super(dao, form(Comment.class), Long.class, Comment.class);
	}

	@Override
	protected String templateForForm() {
		return "commentForm";
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
		return crudIndex();
	}

	public static Call crudIndex() {
		return routes.Admin.commentList();
	}

}
