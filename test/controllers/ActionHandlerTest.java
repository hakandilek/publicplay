package controllers;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collection;

import models.Action;
import models.ActionType;
import models.Post;
import models.User;
import models.dao.ActionDAO;
import models.dao.PostDAO;
import models.dao.UserDAO;

import org.junit.Test;

import test.IntegrationTest;

public class ActionHandlerTest extends IntegrationTest {

	@Test
	public void testCreatePost() {
		ActionHandler actionHandler = getInstance(ActionHandler.class);
		ActionDAO actionDAO = getInstance(ActionDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		Post post = postDAO.get(-11L);
		User user = userDAO.get("facebook::testuser");

		actionHandler.perform(user, post, ActionType.CREATE_POST);
		Collection<Action> actions = actionDAO.getActionsCreatedBy(user);
		assertThat(actions.size()).isEqualTo(1);
		assertThat(actions.toArray(new Action[0])[0].getType()).isEqualTo(ActionType.CREATE_POST);
	}
}
