package models.dao;

import java.util.concurrent.Callable;

import com.pickleproject.shopping.ConfigurationDAO;

import models.SourceConfiguration;
import play.Logger;
import play.Logger.ALogger;
import play.cache.Cache;
import play.utils.cache.CachedFinder;
import play.utils.dao.CachedDAO;
import play.utils.dao.EntityNotFoundException;
import play.utils.dao.TimestampDAO;

public class SourceConfigurationDAO extends TimestampDAO<Long, SourceConfiguration> implements ConfigurationDAO<Long, SourceConfiguration> {
	
	private static ALogger log = Logger.of(SourceConfigurationDAO.class);

	/** expire in 7*24 hours */
	private static final int CACHE_EXPIRATION = 7 * 24 * 3600;

	public SourceConfigurationDAO() {
		super(new CachedDAO<Long, SourceConfiguration>(Long.class, SourceConfiguration.class));
	}

	public SourceConfiguration getWithSourceKey(final String sourceKey) {
		String cacheKey = "SourceConfigurationDAO.bySourceKey." + sourceKey;
		try {
			return Cache.getOrElse(cacheKey, new Callable<SourceConfiguration>() {
				public SourceConfiguration call() throws Exception {
					return find().where().eq("sourceKey", sourceKey).findUnique();
				}
			}, CACHE_EXPIRATION);
		} catch (Exception e) {
			log.error("exception occured while retrieving from cache", e);
			return null;
		}
	}

	@Override
	public Long create(SourceConfiguration m) {
		Long k = super.create(m);
		String sourceKey = m.getSourceKey();
		String cacheKey = "SourceConfigurationDAO.bySourceKey." + sourceKey;
		Cache.set(cacheKey, m);
		return k;
	}

	@Override
	public void remove(Long key) throws EntityNotFoundException {
		SourceConfiguration ref = find().ref(key);
		if (ref == null) throw new EntityNotFoundException(key);
		String sourceKey = ref.getSourceKey();
		String cacheKey = "SourceConfigurationDAO.bySourceKey." + sourceKey;
		Cache.set(cacheKey, null);
		ref.delete();
		CachedFinder<Long, SourceConfiguration> find = (CachedFinder<Long, SourceConfiguration>) find();
		find.clean(key);
	}

	@Override
	public void update(Long key, SourceConfiguration m) {
		super.update(key, m);
		String sourceKey = m.getSourceKey();
		String cacheKey = "SourceConfigurationDAO.bySourceKey." + sourceKey;
		Cache.set(cacheKey, m);
	}

}
