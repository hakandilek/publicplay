package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.Map;
import java.util.Set;

import models.Category;
import models.Post;
import models.dao.CategoryDAO;

import org.junit.Test;

import test.BaseTest;

public class CategoryDAOTest extends BaseTest {

	public CategoryDAOTest() {
		super();
	}

	@Test
	public void testOptions() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				CategoryDAO dao = getInstance(CategoryDAO.class);

				Map<String, String> options = dao.options();
				assertThat(options).isNotNull();
				assertThat(options.size()).isEqualTo(5);
			}
		});
	}

	@Test
	public void testUpdate() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				CategoryDAO dao = getInstance(CategoryDAO.class);
				Category oldCat = dao.get("category1");
				Set<Post> posts = oldCat.getPosts();
				int count = posts.size();

				Category newCat = new Category();
				newCat.setName("testCategory");
				
				//perform operation
				dao.update("category1", newCat);
				
				//verify
				Category newCat2 = dao.get("testCategory");
				assertThat(newCat2).isNotNull();
				assertThat(newCat2.getPosts()).isNotNull();
				assertThat(newCat2.getPosts().size()).isEqualTo(count);
				
				//clean cache and re-verify
				dao.cacheClean("testCategory");
				Category newCat3 = dao.get("testCategory");
				assertThat(newCat3).isNotNull();
				assertThat(newCat3.getPosts()).isNotNull();
				assertThat(newCat3.getPosts().size()).isEqualTo(count);
			}
		});
	}
}
