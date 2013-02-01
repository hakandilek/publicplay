package controllers.crud;

import models.SecurityRole;
import models.dao.SecurityRoleDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;

public class SecurityRoleController extends CRUDController<Long, SecurityRole> {

	public SecurityRoleController(SecurityRoleDAO dao) {
		super(dao, form(SecurityRole.class), Long.class, SecurityRole.class);
	}

	@Override
	protected String templateForForm() {
		return "securityRoleForm";
	}

	@Override
	protected String templateForList() {
		return "securityRoleList";
	}

	@Override
	protected String templateForShow() {
		return "securityRoleShow";
	}

	@Override
	protected Call toIndex() {
		return crudIndex();
	}

	public static Call crudIndex() {
		return CategoryController.crudIndex();
	}

}
