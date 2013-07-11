package controllers;


import javax.inject.Inject;

import karma.AbstractReputationHandler;
import models.ReputationType;
import models.User;
import models.UserReputation;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.UserReputationDAO;
import reputation.CreatePostEvaluation;
import reputation.RateDownEvaluation;
import reputation.RateUpEvaluation;

public class ReputationHandler extends AbstractReputationHandler<User, UserReputation> {

	ReputationValueDAO reputationValueDAO;
	UserReputationDAO userReputationDAO;
	ReputationDAO reputationDAO;

	@Inject
	public ReputationHandler(ReputationDAO reputationDAO,
			ReputationValueDAO reputationValueDAO,
			UserReputationDAO userReputationDAO) {
		super(userReputationDAO);
		this.reputationDAO = reputationDAO;
		this.reputationValueDAO = reputationValueDAO;
		this.userReputationDAO = userReputationDAO;

		addImpact(ReputationType.CREATE_POST, new CreatePostEvaluation(
				reputationDAO, reputationValueDAO, userReputationDAO));
		addImpact(ReputationType.RATE_UP, new RateUpEvaluation(
				reputationDAO, reputationValueDAO, userReputationDAO));
		addImpact(ReputationType.RATE_DOWN, new RateDownEvaluation(
				reputationDAO, reputationValueDAO, userReputationDAO));
	}
}
