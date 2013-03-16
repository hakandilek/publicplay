package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import models.Comment;
import models.dao.CommentDAO;

import org.junit.Test;

import test.BaseTest;

import com.avaje.ebean.Page;

public class CommentDAOTest extends BaseTest {

	public CommentDAOTest() {
		super();
	}

	@Test
	public void testPage() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				CommentDAO dao = getInstance(CommentDAO.class);

				Page<Comment> page = dao.page(-1L, 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(0);
				assertThat(page.getTotalRowCount()).isEqualTo(0);

				page = dao.page(-11L, 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(1);
				assertThat(page.getTotalRowCount()).isEqualTo(4);

				page = dao.page(-11L, 1, 3);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(1);
				assertThat(page.getTotalPageCount()).isEqualTo(2);
				assertThat(page.getTotalRowCount()).isEqualTo(4);
			}
		});
	}

	@Test
	public void testGetCommentsBy() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				CommentDAO dao = getInstance(CommentDAO.class);
				
				Page<Comment> page = dao.getCommentsBy("testuser", 0, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(0);
				assertThat(page.getTotalPageCount()).isEqualTo(3);
				assertThat(page.getTotalRowCount()).isEqualTo(26);

				page = dao.getCommentsBy("testuser", 2, 10);
				assertThat(page).isNotNull();
				assertThat(page.getPageIndex()).isEqualTo(2);
				assertThat(page.getTotalPageCount()).isEqualTo(3);
				assertThat(page.getTotalRowCount()).isEqualTo(26);
			}
		});
	}
}
