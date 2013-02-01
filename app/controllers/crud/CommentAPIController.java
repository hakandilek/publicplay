package controllers.crud;

import javax.inject.Inject;

import models.Comment;
import models.dao.CommentDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class CommentAPIController extends APIController<Long, Comment> {


	@Inject
	public CommentAPIController(CommentDAO commentDAO) {
		super(commentDAO);
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
		
		Comment m = new Comment();
		m.setStatus(Comment.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
