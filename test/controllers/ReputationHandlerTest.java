package controllers;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;

import models.Post;
import models.Reputation;
import models.ReputationType;
import models.ReputationValue;
import models.User;

import org.junit.Test;

import reputation.ReputationContext;
import test.IntegrationTest;

public class ReputationHandlerTest extends IntegrationTest {
	
	@Test
	public void testRateUp() {
		test(new Runnable() {
			public void run() {
				ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
				
				Post post= postDAO.get((long) -101);
				User user = userDAO.get("testuser2");
				ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.RATE_UP);
				
				reputationHandler.evaluate(new ReputationContext(post), ReputationType.RATE_UP);
				
				Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
				assertThat(reputations.size()).isEqualTo(1);
				assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.RATE_UP.getKey());
			}
		});
	}

	
	@Test
	public void testRateDown() {
		test(new Runnable() {
			public void run() {
				ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
				
				Post post= postDAO.get((long) -102);
				User user = userDAO.get("testuser2");
				ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.RATE_DOWN);
				
				reputationHandler.evaluate(new ReputationContext(post), ReputationType.RATE_DOWN);
				
				Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
				assertThat(reputations.size()).isEqualTo(1);
				assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.RATE_DOWN.getKey());
			}
		});
	}
	
	@Test
	public void testCreatePost() {
		test(new Runnable() {
			public void run() {
				ReputationHandler reputationHandler = getInstance(ReputationHandler.class);
				
				Post post= postDAO.get((long) -101);
				User user = userDAO.get("testuser2");
				ReputationValue rateUpValue = reputationValueDAO.get(ReputationType.CREATE_POST);
				
				reputationHandler.evaluate(new ReputationContext(post), ReputationType.CREATE_POST);
				
				Collection<Reputation> reputations = reputationDAO.findFor(user, rateUpValue);
				assertThat(reputations.size()).isEqualTo(1);
				assertThat(reputations.iterator().next().getReputationValueKey()).isEqualTo(ReputationType.CREATE_POST.getKey());
			}
		});
	}
}
