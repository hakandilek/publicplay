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
		test(new Runnable() {
			public void run() {
				Map<String, String> options = categoryDAO.options();
				assertThat(options).isNotNull();
				assertThat(options.size()).isGreaterThan(1);
			}
		});
	}


	@Test
	public void testUpdate() {
		test(new Runnable() {
			public void run() {
				Category oldCat = categoryDAO.get("categoryA");
				Set<Post> posts = oldCat.getPosts();
				int count = posts.size();

				Category newCat = new Category();
				newCat.setName("testCategory");
				
				//perform operation
				categoryDAO.update("categoryA", newCat);
				
				//verify
				Category newCat2 = categoryDAO.get("testCategory");
				assertThat(newCat2).isNotNull();
				assertThat(newCat2.getPosts()).isNotNull();
				assertThat(newCat2.getPosts().size()).isEqualTo(count);
				
				//clean cache and re-verify
				categoryDAO.cacheClean("testCategory");
				Category newCat3 = categoryDAO.get("testCategory");
				assertThat(newCat3).isNotNull();
				assertThat(newCat3.getPosts()).isNotNull();
				assertThat(newCat3.getPosts().size()).isEqualTo(count);
			}
		});
	}
}
