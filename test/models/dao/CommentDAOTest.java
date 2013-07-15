package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import models.Comment;

import org.junit.Test;

import test.IntegrationTest;

import com.avaje.ebean.Page;

public class CommentDAOTest extends IntegrationTest {

	@Test
	public void testPage() {
		test(new Runnable() {
			public void run() {
				CommentDAO dao = getInstance(CommentDAO.class);

				Page<Comment> page = dao.page(-1L, 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(0);
				assertThat(page.getTotalRowCount()).isEqualTo(0);

				page = dao.page(-101L, 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(4);

				page = dao.page(-101L, 1, 3);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(1);
				assertThat(page.getTotalPageCount()).isEqualTo(2);
				assertThat(page.getTotalRowCount()).isEqualTo(4);
			}
		});
	}

	@Test
	public void testGetCommentsBy() {
		test(new Runnable() {
			public void run() {
				CommentDAO dao = getInstance(CommentDAO.class);
				
				Page<Comment> page = dao.getCommentsBy("testuser2", 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(6);

				page = dao.getCommentsBy("testuser2", 1, 4);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(1);
				assertThat(page.getTotalPageCount()).isEqualTo(2);
				assertThat(page.getTotalRowCount()).isEqualTo(6);
			}
		});
	}
}
