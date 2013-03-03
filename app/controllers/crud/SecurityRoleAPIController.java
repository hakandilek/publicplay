package controllers.crud;

import static play.libs.Json.toJson;

import java.util.List;

import javax.inject.Inject;

import models.SecurityRole;
import models.dao.SecurityRoleDAO;
import play.mvc.Result;
import play.utils.crud.APIController;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;


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

	public Result list() {
		List<SecurityRole> all = dao.all();
		Builder<Object> b = ImmutableList.builder();
		for (SecurityRole role : all) {
			b.add(ImmutableMap.of("value", ""+role.getKey(), "text", role.getName()));
		}
		return ok(toJson(b.build()));
	}

}
