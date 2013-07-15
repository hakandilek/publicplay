package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import models.User;
import models.UserFollow;
import models.UserFollowPK;

import org.junit.Test;

import test.IntegrationTest;

import com.avaje.ebean.Page;

public class UserFollowDAOTest extends IntegrationTest {

	@Test
	public void getFollowerCountSucceeds() {
		test(new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);

				User testUser = userDAO.get("testuser2");
				assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(0);

				UserFollowPK followKey = new UserFollowPK("testuser3", "testuser2");
				createUserFollow(userFollowDAO, followKey);
				assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(1);
			}
		});
	}

	@Test
	public void getFollowingCountSucceeds() {
		test(new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);

				User testUser = userDAO.get("testuser2");
				assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(0);

				UserFollowPK followKey = new UserFollowPK("testuser2", "testuser3");
				createUserFollow(userFollowDAO, followKey);

				assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(1);
			}
		});
	}

	@Test
	public void getFollowingUsersSucceeds() {
		test(new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);

				User testUser = userDAO.get("testuser2");
				assertThat(userFollowDAO.getFollowingUsers(testUser, 0).getList().size()).isEqualTo(0);

				UserFollowPK followKey = new UserFollowPK("testuser2", "testuser3");
				createUserFollow(userFollowDAO, followKey);

				Page<User> followingUsers = userFollowDAO.getFollowingUsers(testUser, 0);
				assertThat(followingUsers.getList().size()).isEqualTo(1);
				for (User user : followingUsers.getList()) {
					assertThat(user.getKey()).isEqualTo("testuser3");
				}
			}
		});
	}

	@Test
	public void getFollowerUsersSucceeds() {
		test(new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);

				User testUser = userDAO.get("testuser2");
				assertThat(userFollowDAO.getFollowerUsers(testUser, 0).getList().size()).isEqualTo(0);

				UserFollowPK followKey = new UserFollowPK("testuser3", "testuser2");
				createUserFollow(userFollowDAO, followKey);

				Page<User> followerUsers = userFollowDAO.getFollowerUsers(testUser, 0);
				assertThat(followerUsers.getList().size()).isEqualTo(1);
				for (User user : followerUsers.getList()) {
					assertThat(user.getKey()).isEqualTo("testuser3");
				}
			}
		});
	}

	@Test
	public void getAllFollowingsKeysSucceeds() {
		test(new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);

				User testUser = userDAO.get("testuser2");
				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(0);

				UserFollowPK followKey = new UserFollowPK("testuser2", "testuser3");
				createUserFollow(userFollowDAO, followKey);

				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(1);
				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).get(0)).isEqualTo("testuser3");
			}
		});
	}

	private void createUserFollow(UserFollowDAO userFollowDAO, UserFollowPK followKey) {
		UserFollow follow = userFollowDAO.get(followKey);
		if (follow == null) {
			follow = new UserFollow();
			follow.setKey(followKey);
			userFollowDAO.create(follow);
		}
	}
}
