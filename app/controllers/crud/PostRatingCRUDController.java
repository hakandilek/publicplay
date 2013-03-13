package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.PostRating;
import models.PostRatingPK;
import models.dao.PostRatingDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class PostRatingCRUDController extends CRUDController<PostRatingPK, PostRating> {

	@Inject
	public PostRatingCRUDController(PostRatingDAO dao) {
		super(dao, form(PostRating.class), PostRatingPK.class, PostRating.class, 20, "updatedOn desc");
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
		return routes.Admin.index();
	}

}
