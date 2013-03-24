package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import models.User;
import models.UserFollow;
import models.UserFollowPK;

import org.junit.Test;

import com.avaje.ebean.Page;

import test.BaseTest;

public class UserFollowDAOTest extends BaseTest {

	public UserFollowDAOTest() {
		super();
	}
	
	@Test
	public void getFollowerCountSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO=getInstance(UserDAO.class);
				
				User testUser=userDAO.get("testuser");
				assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(0);
				
				UserFollowPK followKey = new UserFollowPK("new_user", "testuser");
				createUserFollow(userFollowDAO, followKey);
				assertThat(userFollowDAO.getFollowerCount(testUser)).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void getFollowingCountSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO=getInstance(UserDAO.class);
				
				User testUser=userDAO.get("testuser");
				assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(0);
				
				UserFollowPK followKey = new UserFollowPK("testuser","new_user");
				createUserFollow(userFollowDAO, followKey);
				
				assertThat(userFollowDAO.getFollowingCount(testUser)).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void getFollowingUsersSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO=getInstance(UserDAO.class);
				
				User testUser=userDAO.get("testuser");
				assertThat(userFollowDAO.getFollowingUsers(testUser,0).getList().size()).isEqualTo(0);
				
				UserFollowPK followKey = new UserFollowPK("testuser","new_user");
				createUserFollow(userFollowDAO, followKey);
				
				Page<User> followingUsers = userFollowDAO.getFollowingUsers(testUser,0);
				assertThat(followingUsers.getList().size()).isEqualTo(1);
				for(User user: followingUsers.getList()){
					assertThat(user.getKey()).isEqualTo("new_user");
				}
			}
		});
	}
	
	@Test
	public void getFollowerUsersSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO=getInstance(UserDAO.class);
				
				User testUser=userDAO.get("testuser");
				assertThat(userFollowDAO.getFollowerUsers(testUser,0).getList().size()).isEqualTo(0);
				
				UserFollowPK followKey = new UserFollowPK("new_user","testuser");
				createUserFollow(userFollowDAO, followKey);
				
				Page<User> followerUsers = userFollowDAO.getFollowerUsers(testUser,0);
				assertThat(followerUsers.getList().size()).isEqualTo(1);
				for(User user: followerUsers.getList()){
					assertThat(user.getKey()).isEqualTo("new_user");
				}
			}
		});
	}
	
	@Test
	public void getAllFollowingsKeysSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				UserFollowDAO userFollowDAO = getInstance(UserFollowDAO.class);
				UserDAO userDAO=getInstance(UserDAO.class);
				
				User testUser=userDAO.get("testuser");
				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(0);
				
				UserFollowPK followKey = new UserFollowPK("testuser","new_user");
				createUserFollow(userFollowDAO, followKey);
				
				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).size()).isEqualTo(1);
				assertThat(userFollowDAO.getAllFollowingsKeys(testUser).get(0)).isEqualTo("new_user");
			}
		});
	}
	
	private void createUserFollow(UserFollowDAO userFollowDAO,
			UserFollowPK followKey) {
		UserFollow follow = userFollowDAO.get(followKey);
		if (follow == null) {
			follow = new UserFollow();
			follow.setKey(followKey);
			userFollowDAO.create(follow);
		}
	}
}
