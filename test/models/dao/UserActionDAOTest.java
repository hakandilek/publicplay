package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.Collection;

import models.Action;
import models.Post;
import models.User;

import org.junit.Test;

import test.BaseTest;

public class UserActionDAOTest extends BaseTest {
	
	public UserActionDAOTest() {
		super();
	}
	
	@Test
	public void testAddReputationToUser() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserActionDAO userActionDAO = getInstance(UserActionDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				
				Post post= postDAO.get((long) -11);
				User user = userDAO.get("testuser");
				
				userActionDAO.addUserAction(user, post,"rateUp");
				Collection<Action> actions = userActionDAO.getActionsCreatedBy(user);
				assertThat(actions.size()).isEqualTo(1);
				assertThat(actions.toArray(new Action[0])[0].getName()).isEqualTo("rateUp");
			}
		});
	}
}
