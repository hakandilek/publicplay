package models.dao;

import javax.inject.Singleton;

import javax.inject.Inject;

import models.Reputation;
import models.ReputationValue;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.utils.dao.CachedDAO;

@Singleton
public class ReputationDAO extends CachedDAO<Long, Reputation> {

	private static ALogger log = Logger.of(ReputationDAO.class);
	ReputationValueDAO reputationValueDAO;
	
	@Inject
	public ReputationDAO(ReputationValueDAO reputationValueDAO) {
		super(Long.class, Reputation.class);
		this.reputationValueDAO = reputationValueDAO;
	}

	public void addReputationToUser(User createdBy, String name) {
		ReputationValue reputationValue = reputationValueDAO.get(name);
		if (null != reputationValue) {
			Reputation reputation = new Reputation();
			reputation.setCreatedBy(createdBy);
			reputation.setValue(reputationValue.getValue());
			this.create(reputation);
		}

		
		if (log.isDebugEnabled())
			log.error(String.format("Invalid reputation name {0}", name));

	}

}
