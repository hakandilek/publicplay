package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.Post;
import models.dao.PostDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class PostCRUDController extends CRUDController<Long, Post> {

	@Inject
	public PostCRUDController(PostDAO postDAO) {
		super(postDAO, form(Post.class), Long.class, Post.class, 20, "updatedOn desc");
	}

	@Override
	protected String templateForForm() {
		return "admin.postForm";
	}

	@Override
	protected String templateForList() {
		return "admin.postList";
	}

	@Override
	protected String templateForShow() {
		return "admin.postShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.postList(0);
	}

}