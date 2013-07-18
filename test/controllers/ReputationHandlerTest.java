package controllers;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;

import models.Post;
import models.Reputation;
import models.ReputationType;
import models.ReputationValue;
import models.User;
import models.dao.PostDAO;
import models.dao.ReputationDAO;
import models.dao.ReputationValueDAO;
import models.dao.UserDAO;

import org.junit.Test;

import reputation.ReputationContext;
import test.IntegrationTest;

public class ReputationHandlerTest extends IntegrationTest {

	@Test
	public void testRateUp() {
		ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
		ReputationDAO reputationDAO = getInstance(ReputationDAO.class);
		ReputationValueDAO reputationValueDAO = getInstance(ReputationValueDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);

		Post post = postDAO.get(-11L);
		User user = userDAO.get("facebook::testuser");
		ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.RATE_UP);

		reputationHandler.evaluate(new ReputationContext(post), ReputationType.RATE_UP);

		Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
		assertThat(reputations.size()).isEqualTo(1);
		assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.RATE_UP.getKey());
	}

	@Test
	public void testRateDown() {
		ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
		ReputationDAO reputationDAO = getInstance(ReputationDAO.class);
		ReputationValueDAO reputationValueDAO = getInstance(ReputationValueDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);

		Post post = postDAO.get(-12L);
		User user = userDAO.get("facebook::testuser");
		ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.RATE_DOWN);

		reputationHandler.evaluate(new ReputationContext(post), ReputationType.RATE_DOWN);

		Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
		assertThat(reputations.size()).isEqualTo(1);
		assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.RATE_DOWN.getKey());
	}

	@Test
	public void testCreatePost() {
		ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
		ReputationDAO reputationDAO = getInstance(ReputationDAO.class);
		ReputationValueDAO reputationValueDAO = getInstance(ReputationValueDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);

		Post post = postDAO.get(-11L);
		User user = userDAO.get("facebook::testuser");
		ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.CREATE_POST);

		reputationHandler.evaluate(new ReputationContext(post), ReputationType.CREATE_POST);

		Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
		assertThat(reputations.size()).isEqualTo(1);
		assertThat(reputations.iterator().next().getReputationValueKey())
				.isEqualTo(ReputationType.CREATE_POST.getKey());
	}
}
