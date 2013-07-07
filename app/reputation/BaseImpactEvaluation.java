package reputation;

import karma.AbstractImpactEvaluation;
import models.Post;
import models.Reputation;
import models.ReputationType;
import models.User;
import models.UserReputation;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.UserReputationDAO;

public class BaseImpactEvaluation extends
		AbstractImpactEvaluation<ReputationContext, Reputation, User, UserReputation> {

	public BaseImpactEvaluation(ReputationDAO reputationDAO,
			ReputationValueDAO reputationValueDAO,
			UserReputationDAO userReputationDAO, ReputationType reputationType) {
		super(reputationDAO, reputationValueDAO, userReputationDAO,
				reputationType);
	}

	@Override
	protected Reputation createReputationEntry() {
		return new Reputation();
	}

	@Override
	protected User subject(ReputationContext ctx) {
		Post post = ctx.getPost();
		User user = post.getCreatedBy();
		return user;
	}

}
