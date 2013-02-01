package controllers.crud;

import javax.inject.Inject;

import models.SecurityRole;
import models.dao.SecurityRoleDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class SecurityRoleAPIController extends APIController<Long, SecurityRole> {


	@Inject
	public SecurityRoleAPIController(SecurityRoleDAO securityRoleDAO) {
		super(securityRoleDAO);
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
		
		SecurityRole m = new SecurityRole();
		m.setStatus(SecurityRole.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
