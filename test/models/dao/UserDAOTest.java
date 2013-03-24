package models.dao;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.User;
import models.User.Status;

import org.junit.Test;

import test.BaseTest;

public class UserDAOTest extends BaseTest {

	public UserDAOTest() {
		super();
	}

	@Test
	public void getPageOfUsersWithOrderOfLastLogin() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserDAO userDAO=getInstance(UserDAO.class);
				//Approved users
				List<User> list = userDAO.page(0, 2, Status.APPROVED).getList();
				assertThat(list.size()).isEqualTo(2);
				assertThat(list.get(0).getFirstName()).isEqualTo("Necip");
			}
		});
	}
}