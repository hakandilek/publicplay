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
//
//	public int recalculateReputationFor(User user) {
//		int reputation = 0;
//		ReputationValue rating = reputationValueDAO.get(RATING);
//		ReputationValue createPost = reputationValueDAO.get(CREATE_POST);
//		if (rating != null && createPost != null) {
//			int ratingValue = rating.getNumberValue();
//			int postCreateValue = createPost.getNumberValue();
//			Collection<Action> actions = actionDAO.getActionsCreatedBy(user);
//			for (Action action : actions) {
//				if (action.getType().equals(CREATE_POST)) {
//					int postRating = action.getTargetPost().getRating();
//					reputation += postRating * ratingValue;
//					reputation += postCreateValue;
//				}
//			}
//		} else {
//			if (log.isDebugEnabled())
//				log.error(String
//						.format("There exist no reputation value for createPost or rating"));
//		}
//		return reputation;
//	}
//
//	public void updateReputationFor(User user) {
//		int reputation = recalculateReputationFor(user);
//		UserReputation ur = userReputationDAO.get(user);
//		if (ur == null) {
//			ur = new UserReputation(user, 0);
//		}
//		ur.setValue(reputation);
//		userReputationDAO.update(ur);
//	}
}
