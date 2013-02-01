package controllers.crud;

import javax.inject.Inject;

import models.User;
import models.dao.UserDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class UserAPIController extends APIController<String, User> {

	@Inject
	public UserAPIController(UserDAO userDAO) {
		super(userDAO);
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
		
		User m = new User();
		m.setStatus(User.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
