package models.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;

import org.junit.Test;

import test.IntegrationTest;

import com.avaje.ebean.Page;

public class PostRatingDAOTest extends IntegrationTest {

	@Test
	public void getRatingFromUserAndPostSucceeds() {
		User user = getInstance(UserDAO.class).get("facebook::testuser");
		Post post = getInstance(PostDAO.class).get(-11L);

		PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
		PostRating pr = new PostRating();
		pr.setValue(5);
		pr.setKey(key);

		PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
		postRatingDAO.create(pr);

		PostRating postRating = postRatingDAO.get(user, post);
		assertThat(postRating).isNotNull();
		assertThat(postRating.getValue()).isEqualTo(5);
	}

	@Test
	public void getRatingFromUserSucceeds() {
		PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);

		User user = userDAO.get("facebook::testuser");
		List<PostRating> postRatingList = postRatingDAO.get(user);
		assertThat(postRatingList.size()).isEqualTo(0);

		PostRatingPK key = new PostRatingPK(user.getKey(), (long) 2);
		PostRating pr = new PostRating();
		pr.setValue(5);
		pr.setKey(key);

		postRatingDAO.create(pr);
		postRatingList = postRatingDAO.get(user);
		assertThat(postRatingList.size()).isEqualTo(1);
	}

	@Test
	public void getUpVotedKeysSucceeds() {
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);
		PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);

		User user = userDAO.get("facebook::testuser");
		Set<Long> upVotedKeys = postRatingDAO.getUpVotedPostKeys(user);
		assertThat(upVotedKeys.size()).isEqualTo(0);

		Post post = postDAO.get(-11L);

		PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
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

	@Test
	public void getDownVotedKeysSucceeds() {
		PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);

		User user = userDAO.get("facebook::testuser");
		Set<Long> upVotedKeys = postRatingDAO.getDownVotedPostKeys(user);
		assertThat(upVotedKeys.size()).isEqualTo(0);

		Post post = postDAO.get(-11L);

		PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
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

	@Test
	public void getUpVotedPostsSucceeds() {
		PostRatingDAO postRatingDAO = getInstance(PostRatingDAO.class);
		UserDAO userDAO = getInstance(UserDAO.class);
		PostDAO postDAO = getInstance(PostDAO.class);

		User user = userDAO.get("facebook::testuser");
		Page<Post> upVotedPosts = postRatingDAO.getUpVotedPosts(user, 0);
		assertThat(upVotedPosts.getList().size()).isEqualTo(0);

		Post post = postDAO.get(-11L);

		PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
		PostRating pr = new PostRating();
		pr.setValue(1);
		pr.setKey(key);
		pr.setKey(key);
		postRatingDAO.create(pr);
		post.setRating(post.getRating() + 1);
		postRatingDAO.resetVotedPostKeyCache(user);

		upVotedPosts = postRatingDAO.getUpVotedPosts(user, 0);
		assertThat(upVotedPosts.getList().size()).isEqualTo(1);
	}

}