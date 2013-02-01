package controllers.crud;

import models.Comment;
import models.dao.CommentDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class CommentController extends CRUDController<Long, Comment> {

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
		return CategoryController.crudIndex();
	}

}
