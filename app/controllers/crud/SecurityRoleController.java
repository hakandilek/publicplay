package controllers.crud;

import javax.inject.Inject;

import models.SecurityRole;
import models.dao.SecurityRoleDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class SecurityRoleController extends CRUDController<Long, SecurityRole> {

	@Inject
	public SecurityRoleController(SecurityRoleDAO dao) {
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
