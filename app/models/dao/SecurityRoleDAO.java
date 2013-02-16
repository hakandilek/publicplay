package models.dao;

import javax.inject.Singleton;

import models.SecurityRole;
import play.utils.dao.CachedDAO;

@Singleton
public class SecurityRoleDAO extends CachedDAO<Long, SecurityRole> {

	public SecurityRoleDAO() {
		super(Long.class, SecurityRole.class);
	}

}
