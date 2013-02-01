package controllers.crud;

import javax.inject.Inject;

import models.Post;
import models.dao.PostDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class PostAPIController extends APIController<Long, Post> {


	@Inject
	public PostAPIController(PostDAO postDAO) {
		super(postDAO);
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
		
		Post m = new Post();
		m.setStatus(Post.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
