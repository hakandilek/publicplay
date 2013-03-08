package models.dao;

import javax.inject.Singleton;

import models.ReputationValue;
import play.utils.dao.CachedDAO;
import javax.inject.Inject;

@Singleton
public class ReputationValueDAO extends CachedDAO<String, ReputationValue> {

	@Inject
	public ReputationValueDAO() {
		super(String.class, ReputationValue.class);
	}


}
