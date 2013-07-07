package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.Collection;

import models.Action;
import models.ActionType;
import models.Post;
import models.User;
import models.dao.ActionDAO;
import models.dao.PostDAO;
import models.dao.UserDAO;

import org.junit.Test;

import controllers.ActionHandler;

import test.BaseTest;

public class ActionHandlerTest extends BaseTest {
	
	public ActionHandlerTest() {
		super();
	}
	
	@Test
	public void testCreatePost() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ActionHandler actionHandler = getInstance(ActionHandler.class);
				ActionDAO actionDAO = getInstance(ActionDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				
				Post post= postDAO.get((long) -11);
				User user = userDAO.get("testuser");
				
				actionHandler.perform(user, post, ActionType.CREATE_POST);
				Collection<Action> actions = actionDAO.getActionsCreatedBy(user);
				assertThat(actions.size()).isEqualTo(1);
				assertThat(actions.toArray(new Action[0])[0].getType()).isEqualTo(ActionType.CREATE_POST);
			}
		});
	}
}
