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
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);

				Page<Post> page = dao.page(0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isGreaterThanOrEqualTo(1);
				assertThat(page.getTotalRowCount()).isGreaterThanOrEqualTo(3);

				page = dao.page(1, 2);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(1);
				assertThat(page.getTotalPageCount()).isGreaterThanOrEqualTo(2);
				assertThat(page.getTotalRowCount()).isGreaterThanOrEqualTo(3);
			}
		});
	}

	@Test
	public void testGetPostsBy() {
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);
				Category category = new Category("categoryA");
				
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
		});
	}

	@Test
	public void testTopDayPage() {
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);
				Post post = new Post();
				dao.create(post);
				
				Page<Post> page = dao.topDayPage();
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(4);
			}
		});
	}

	@Test
	public void testTopWeekPage() {
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);
				Post post = new Post();
				dao.create(post);
				
				Page<Post> page = dao.topWeekPage();
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(4);
			}
		});
	}

	@Test
	public void testTopAllPage() {
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);
				
				Page<Post> page = dao.topAllPage();
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isGreaterThanOrEqualTo(1);
				assertThat(page.getTotalRowCount()).isGreaterThanOrEqualTo(3);
			}
		});
	}

	@Test
	public void testGetPostsCreatedBy() {
		test(new Runnable() {
			public void run() {
				PostDAO dao = getInstance(PostDAO.class);
				
				List<String> usernames = ImmutableList.of("testuser2");
				Page<Post> page = dao.getPostsCreatedBy(usernames, 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(3);
			}
		});
	}
}
