package models.dao;

import javax.inject.Inject;

import models.Owned;
import models.User;
import play.utils.cache.CachedFinder;
import play.utils.dao.DAOListener;

public class OwnerCacheCleaner<K, M extends Owned<K>> implements
		DAOListener<K, M> {

	private CachedFinder<String, User> ownerCache;

	public OwnerCacheCleaner(CachedFinder<String, User> ownerCache) {
		this.ownerCache = ownerCache;
	}

	@Inject
	public OwnerCacheCleaner(UserDAO userDAO) {
		this(userDAO.cacheFind());
	}

	@Override
	public void afterCreate(K key, M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			ownerCache.clean(owner.getKey());
	}

	@Override
	public void afterRemove(K key, M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			ownerCache.clean(owner.getKey());
	}

	@Override
	public void afterUpdate(K key, M m) {
		User owner = m.getCreatedBy();
		if (owner != null)
			ownerCache.clean(owner.getKey());
	}

	@Override
	public void beforeCreate(M m) {
	}

	@Override
	public void beforeRemove(K key) {
	}

	@Override
	public void beforeUpdate(K key, M m) {
	}

}
