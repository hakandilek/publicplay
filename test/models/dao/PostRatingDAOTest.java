package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.List;
import java.util.Set;

import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;

import org.junit.Test;

import com.avaje.ebean.Page;

import test.BaseTest;

public class PostRatingDAOTest extends BaseTest {

	public PostRatingDAOTest() {
		super();
	}

	@Test
	public void getRatingFromUserAndPostSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);

				User user = userDAO.get("testuser");
				Post post = postDAO.get((long) -11);

				PostRatingPK key = new PostRatingPK(user.getKey(), post
						.getKey());
				PostRating pr = new PostRating();
				pr.setValue(5);
				pr.setKey(key);

				postRatingDAO.create(pr);

				PostRating postRating = postRatingDAO.get(user, post);
				assertThat(postRating).isNotNull();
				assertThat(postRating.getValue()).isEqualTo(5);
			}
		});
	}

	@Test
	public void getRatingFromUserSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				
				User user = userDAO.get("testuser");
				List<PostRating> postRatingList = postRatingDAO.get(user);
				assertThat(postRatingList.size()).isEqualTo(0);
				
				PostRatingPK key = new PostRatingPK(user.getKey(),(long)2);
				PostRating pr = new PostRating();
				pr.setValue(5);
				pr.setKey(key);

				postRatingDAO.create(pr);
				postRatingList = postRatingDAO.get(user);
				assertThat(postRatingList.size()).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void getUpVotedKeysSucceeds(){
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				
				User user = userDAO.get("testuser");
				Set<Long> upVotedKeys = postRatingDAO.getUpVotedPostKeys(user);
				assertThat(upVotedKeys.size()).isEqualTo(0);
				
				Post post = postDAO.get((long) -11);
				
				PostRatingPK key = new PostRatingPK(user.getKey(),post.getKey());
				PostRating pr = new PostRating();
				pr.setValue(1);
				pr.setKey(key);
				pr.setKey(key);
				postRatingDAO.create(pr);
				post.setRating(post.getRating() + 1);
				postRatingDAO.resetVotedPostKeyCache(user);
				
				upVotedKeys = postRatingDAO.getUpVotedPostKeys(user);
				assertThat(upVotedKeys.size()).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void getDownVotedKeysSucceeds(){
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				
				User user = userDAO.get("testuser");
				Set<Long> upVotedKeys = postRatingDAO.getDownVotedPostKeys(user);
				assertThat(upVotedKeys.size()).isEqualTo(0);
				
				Post post = postDAO.get((long) -11);
				
				PostRatingPK key = new PostRatingPK(user.getKey(),post.getKey());
				PostRating pr = new PostRating();
				pr.setValue(-1);
				pr.setKey(key);
				pr.setKey(key);
				postRatingDAO.create(pr);
				post.setRating(post.getRating() - 1);
				postRatingDAO.resetVotedPostKeyCache(user);
				
				upVotedKeys = postRatingDAO.getDownVotedPostKeys(user);
				assertThat(upVotedKeys.size()).isEqualTo(1);
			}
		});
	}
	
	@Test
	public void getUpVotedPostsSucceeds(){
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
				UserDAO userDAO = getInstance(UserDAO.class);
				PostDAO postDAO = getInstance(PostDAO.class);
				
				User user = userDAO.get("testuser");
				Page<Post> upVotedPosts = postRatingDAO.getUpVotedPosts(user, 0);
				assertThat(upVotedPosts.getList().size()).isEqualTo(0);
				
				Post post = postDAO.get((long) -11);
				
				PostRatingPK key = new PostRatingPK(user.getKey(),post.getKey());
				PostRating pr = new PostRating();
				pr.setValue(1);
				pr.setKey(key);
				pr.setKey(key);
				postRatingDAO.create(pr);
				post.setRating(post.getRating() + 1);
				postRatingDAO.resetVotedPostKeyCache(user);
				
				upVotedPosts = postRatingDAO.getUpVotedPosts(user,0);
				assertThat(upVotedPosts.getList().size()).isEqualTo(1);
			}
		});
	}
}