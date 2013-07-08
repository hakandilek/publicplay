package models.dao;

import java.util.Collection;
import java.util.concurrent.Callable;

import javax.inject.Singleton;

import karma.model.Impact;
import karma.model.Reputable;
import karma.model.ReputationEntryStore;
import models.Reputation;
import play.utils.cache.InterimCache;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

import com.avaje.ebean.Expr;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@Singleton
public class ReputationDAO extends CachedDAO<Long, Reputation> implements ReputationEntryStore<Long, Reputation> {

	protected InterimCache<Collection<Reputation>> reputationEntryCache = new InterimCache<Collection<Reputation>>(
			"ReputationEntryCache", 86400);// 24 hrs

	protected Multimap<String, String> userKeys = HashMultimap.create();

	protected Multimap<String, String> reputationValueKeys = HashMultimap.create();

	public ReputationDAO() {
		super(Long.class, Reputation.class);
		addListener(new TimestampListener<Long, Reputation>());
		addListener(new ReputationDAOListener(this));
	}

	public Collection<Reputation> findFor(Reputable subject, Impact impact) {
		final String subjectKey = subject.getKey();
		final String impactKey = impact.getKey();
		final String cacheKey = subjectKey + ":" + impactKey;
		return reputationEntryCache.get(cacheKey, new Callable<Collection<Reputation>>() {
			public Collection<Reputation> call() throws Exception {
				userKeys.put(subjectKey, cacheKey);
				reputationValueKeys.put(impactKey, cacheKey);
				return find.where().and(Expr.eq("owner_key", subjectKey), Expr.eq("reputation_value_key", impactKey))
						.findList();
			}
		});
	}

	public void cleanUserCache(String userKey) {
		Collection<String> cacheKeys = userKeys.get(userKey);
		for (String cacheKey : cacheKeys) {
			reputationEntryCache.set(cacheKey, null);
		}
	}

	public void cleanReputationValueCache(String repuationValueKey) {
		Collection<String> cacheKeys = reputationValueKeys.get(repuationValueKey);
		for (String cacheKey : cacheKeys) {
			reputationEntryCache.set(cacheKey, null);
		}
	}
}
