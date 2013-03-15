package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.SecurityRole;
import models.dao.SecurityRoleDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class SecurityRoleCRUDController extends CRUDController<Long, SecurityRole> {

	@Inject
	public SecurityRoleCRUDController(SecurityRoleDAO dao) {
		super(dao, form(SecurityRole.class), Long.class, SecurityRole.class, 20, "name");
	}

	@Override
	protected String templateForForm() {
		return "admin.securityRoleForm";
	}

	@Override
	protected String templateForList() {
		return "admin.securityRoleList";
	}

	@Override
	protected String templateForShow() {
		return "admin.securityRoleShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.securityRoleList(0);
	}

}
