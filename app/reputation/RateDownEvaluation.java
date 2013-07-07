package reputation;

import models.ReputationType;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.UserReputationDAO;

public class RateDownEvaluation extends BaseImpactEvaluation {

	public RateDownEvaluation(ReputationDAO reputationDAO,
			ReputationValueDAO reputationValueDAO,
			UserReputationDAO userReputationDAO) {
		super(reputationDAO, reputationValueDAO, userReputationDAO,
				ReputationType.RATE_DOWN);
	}

}
