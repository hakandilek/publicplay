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
		UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User testUser = userDAO.get("facebook::testuser");
		assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(0);

		UserFollowPK followKey = new UserFollowPK("facebook::new_user", "facebook::testuser");
		createUserFollow(userFollowDAO, followKey);
		assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(1);
	}

	@Test
	public void getFollowingCountSucceeds() {
		UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User testUser = userDAO.get("facebook::testuser");
		assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(0);

		UserFollowPK followKey = new UserFollowPK("facebook::testuser", "facebook::new_user");
		createUserFollow(userFollowDAO, followKey);

		assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(1);
	}

	@Test
	public void getFollowingUsersSucceeds() {
		UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User testUser = userDAO.get("facebook::testuser");
		assertThat(userFollowDAO.getFollowingUsers(testUser, 0).getList().size()).isEqualTo(0);

		UserFollowPK followKey = new UserFollowPK("facebook::testuser", "facebook::new_user");
		createUserFollow(userFollowDAO, followKey);

		Page<User> followingUsers = userFollowDAO.getFollowingUsers(testUser, 0);
		assertThat(followingUsers.getList().size()).isEqualTo(1);
		for (User user : followingUsers.getList()) {
			assertThat(user.getKey()).isEqualTo("facebook::new_user");
		}
	}

	@Test
	public void getFollowerUsersSucceeds() {
		UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User testUser = userDAO.get("facebook::testuser");
		assertThat(userFollowDAO.getFollowerUsers(testUser, 0).getList().size()).isEqualTo(0);

		UserFollowPK followKey = new UserFollowPK("facebook::new_user", "facebook::testuser");
		createUserFollow(userFollowDAO, followKey);

		Page<User> followerUsers = userFollowDAO.getFollowerUsers(testUser, 0);
		assertThat(followerUsers.getList().size()).isEqualTo(1);
		for (User user : followerUsers.getList()) {
			assertThat(user.getKey()).isEqualTo("facebook::new_user");
		}
	}

	@Test
	public void getAllFollowingsKeysSucceeds() {
		UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User testUser = userDAO.get("facebook::testuser");
		assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(0);

		UserFollowPK followKey = new UserFollowPK("facebook::testuser", "facebook::new_user");
		createUserFollow(userFollowDAO, followKey);

		assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(1);
		assertThat(userFollowDAO.getAllFollowingsKeys(testUser).get(0)).isEqualTo("facebook::new_user");
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
