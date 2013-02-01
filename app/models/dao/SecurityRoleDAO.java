package models.dao;

import models.SecurityRole;
import play.utils.dao.CachedDAO;

public class SecurityRoleDAO extends CachedDAO<Long, SecurityRole> {

	public SecurityRoleDAO() {
		super(Long.class, SecurityRole.class);
	}

}
