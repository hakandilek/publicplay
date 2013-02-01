package controllers.crud;

import javax.inject.Inject;

import models.PostRating;
import models.PostRatingPK;
import models.dao.PostRatingDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class PostRatingAPIController extends APIController<PostRatingPK, PostRating> {


	@Inject
	public PostRatingAPIController(PostRatingDAO postRatingDAO) {
		super(postRatingDAO);
	}

	@Override
	public Result create() {
		/* TODO:
		Result check = checkRequired("url");
		if (check != null) {
			return check;
		}

		String url = jsonText("url");
		try {
			new URL(url );
		} catch (MalformedURLException e) {
			return badRequest(toJson(ImmutableMap.of(
					"status", "error", 
					"message", e.getMessage())));
		}
		
		PostRating m = new PostRating();
		m.setStatus(PostRating.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
