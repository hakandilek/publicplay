package reputation;

import models.ReputationType;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.UserReputationDAO;

public class CreatePostEvaluation extends BaseImpactEvaluation {

	public CreatePostEvaluation(ReputationDAO reputationDAO,
			ReputationValueDAO reputationValueDAO,
			UserReputationDAO userReputationDAO) {
		super(reputationDAO, reputationValueDAO, userReputationDAO,
				ReputationType.CREATE_POST);
	}

}
