package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

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
import test.BaseTest;

public class ReputationHandlerTest extends BaseTest {
	
	public ReputationHandlerTest() {
		super();
	}
	
	@Test
	public void testRateUp() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
				ReputationDAO reputationDAO = getInstance(ReputationDAO.class);
				ReputationValueDAO reputationValueDAO = getInstance(ReputationValueDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				
				Post post= postDAO.get((long) -11);
				User user = userDAO.get("testuser");
				ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.RATE_UP);
				
				reputationHandler.evaluate(new ReputationContext(post), ReputationType.RATE_UP);
				
				Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
				assertThat(reputations.size()).isEqualTo(1);
				assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.RATE_UP.getKey());
			}
		});
	}
}
