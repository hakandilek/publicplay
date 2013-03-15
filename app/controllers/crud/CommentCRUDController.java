package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.Comment;
import models.dao.CommentDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class CommentCRUDController extends CRUDController<Long, Comment>  {

	@Inject
	public CommentCRUDController(CommentDAO dao) {
		super(dao, form(Comment.class), Long.class, Comment.class, 20, "updatedOn desc");
	}

	@Override
	protected String templateForForm() {
		return "admin.commentForm";
	}

	@Override
	protected String templateForList() {
		return "admin.commentList";
	}

	@Override
	protected String templateForShow() {
		return "admin.commentShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.commentList(0);
	}

}
