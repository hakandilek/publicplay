package models.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;
import java.util.Set;

import models.Category;
import models.Post;

import org.junit.Test;

import test.IntegrationTest;

public class CategoryDAOTest extends IntegrationTest {

	@Test
	public void testOptions() {
		Map<String, String> options = getInstance(CategoryDAO.class).options();
		assertThat(options).isNotNull();
		assertThat(options.size()).isGreaterThan(1);
	}

	@Test
	public void testUpdate() {
		CategoryDAO categoryDAO = getInstance(CategoryDAO.class);
		
		Category oldCat = categoryDAO.get("category1");
		Set<Post> posts = oldCat.getPosts();
		int count = posts.size();

		Category newCat = new Category();
		newCat.setName("testCategory");

		// perform operation
		categoryDAO.update("category1", newCat);

		// verify
		Category newCat2 = categoryDAO.get("testCategory");
		assertThat(newCat2).isNotNull();
		assertThat(newCat2.getPosts()).isNotNull();
		assertThat(newCat2.getPosts().size()).isEqualTo(count);

		// clean cache and re-verify
		categoryDAO.cacheClean("testCategory");
		Category newCat3 = categoryDAO.get("testCategory");
		assertThat(newCat3).isNotNull();
		assertThat(newCat3.getPosts()).isNotNull();
		assertThat(newCat3.getPosts().size()).isEqualTo(count);
	}
}
