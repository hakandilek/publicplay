package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import karma.model.Impact;
import karma.model.ImpactIdentifier;
import karma.model.ImpactStore;
import models.ReputationType;
import models.ReputationValue;
import play.Logger;
import play.Logger.ALogger;
import play.utils.dao.CachedDAO;

@Singleton
public class ReputationValueDAO extends CachedDAO<String, ReputationValue> implements ImpactStore {

	private static ALogger log = Logger.of(ReputationValueDAO.class);
	
	@Inject
	public ReputationValueDAO() {
		super(String.class, ReputationValue.class);
	}

	public ReputationValue get(ReputationType type) {
		if (log.isDebugEnabled())
			log.debug("type : " + type);

		return get(type.name());
	}

	@Override
	public Impact get(ImpactIdentifier identifier) {
		if (log.isDebugEnabled())
			log.debug("identifier : " + identifier);

		String key = identifier.getKey();
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		return get(key);
	}

}
