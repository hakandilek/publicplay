package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import models.Post;
import models.Reputation;
import models.User;
import models.dao.PostDAO;
import models.dao.ReputationDAO;

import org.junit.Test;

import test.BaseTest;

public class ReputationDAOTest extends BaseTest {
	@Test
	public void testAddReputationToUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ReputationDAO reputationDAO = getInstance(ReputationDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				Post post= postDAO.get((long) -11);
				User user= post.getCreatedBy();
				
				reputationDAO.addReputation(post,"rateUp");
				assertThat(user.getReputations().size()).isEqualTo(1);
				assertThat(user.getReputations().toArray(new Reputation[0])[0].getName()).isEqualTo("rateUp");
			}
		});
	}
}
