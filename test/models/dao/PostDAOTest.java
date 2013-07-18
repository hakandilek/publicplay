package models.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Category;
import models.Post;

import org.junit.Test;

import test.IntegrationTest;

import com.avaje.ebean.Page;
import com.google.common.collect.ImmutableList;

public class PostDAOTest extends IntegrationTest {

	@Test
	public void testPage() {
		PostDAO dao = getInstance(PostDAO.class);

		Page<Post> page = dao.page(0, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(10);

		page = dao.page(1, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(1);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(10);
	}

	@Test
	public void testGetPostsBy() {
		PostDAO dao = getInstance(PostDAO.class);
		Category category = new Category("category1");

		Page<Post> page = dao.pageInCategory(category, 0, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(3);

		page = dao.pageInCategory(category, 1, 2);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(1);
		assertThat(page.getTotalPageCount()).isEqualTo(2);
		assertThat(page.getTotalRowCount()).isEqualTo(3);
	}

	@Test
	public void testTopDayPage() {
		PostDAO dao = getInstance(PostDAO.class);
		Post post = new Post();
		dao.create(post);

		Page<Post> page = dao.topDayPage();
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(1);
	}

	@Test
	public void testTopWeekPage() {
		PostDAO dao = getInstance(PostDAO.class);
		Post post = new Post();
		dao.create(post);

		Page<Post> page = dao.topWeekPage();
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(1);
	}

	@Test
	public void testTopAllPage() {
		PostDAO dao = getInstance(PostDAO.class);

		Page<Post> page = dao.topAllPage();
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(1);
		assertThat(page.getTotalRowCount()).isEqualTo(9);
	}

	@Test
	public void testGetPostsCreatedBy() {
		PostDAO dao = getInstance(PostDAO.class);

		List<String> usernames = ImmutableList.of("facebook::testuser");
		Page<Post> page = dao.getPostsCreatedBy(usernames, 0, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(2);
		assertThat(page.getTotalRowCount()).isEqualTo(11);
	}
}
