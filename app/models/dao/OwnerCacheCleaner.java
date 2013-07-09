package models.dao;

import javax.inject.Inject;

import models.Owned;
import models.User;
import play.utils.dao.DAOListenerAdapter;

public class OwnerCacheCleaner<K, M extends Owned<K>> extends DAOListenerAdapter<K, M> {

	private UserDAO userDAO;

	@Inject
	public OwnerCacheCleaner(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void afterCreate(K key, M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			userDAO.cacheClean(owner.getKey());
	}

	@Override
	public void afterRemove(K key, M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			userDAO.cacheClean(owner.getKey());
	}

	@Override
	public void afterUpdate(M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			userDAO.cacheClean(owner.getKey());
	}

}
