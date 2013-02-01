package controllers.crud;

import models.PostRating;
import models.PostRatingPK;
import models.dao.PostRatingDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class PostRatingController extends CRUDController<PostRatingPK, PostRating> {

	public PostRatingController(PostRatingDAO dao) {
		super(dao, form(PostRating.class), PostRatingPK.class, PostRating.class);
	}

	@Override
	protected String templateForForm() {
		return "postRatingForm";
	}

	@Override
	protected String templateForList() {
		return "postRatingList";
	}

	@Override
	protected String templateForShow() {
		return "postRatingShow";
	}

	@Override
	protected Call toIndex() {
		return crudIndex();
	}

	public static Call crudIndex() {
		return CategoryController.crudIndex();
	}

}
