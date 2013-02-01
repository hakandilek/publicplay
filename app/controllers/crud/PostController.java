package controllers.crud;

import models.Post;
import models.dao.PostDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class PostController extends CRUDController<Long, Post> {

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
		return CategoryController.crudIndex();
	}

}
