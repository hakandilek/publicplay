package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import models.Comment;

import org.junit.Test;

import play.utils.dao.EntityNotFoundException;
import test.IntegrationTest;

import com.avaje.ebean.Page;

public class CommentDAOTest extends IntegrationTest {

	@Test
	public void testDeleteMissing() {
		CommentDAO dao = getInstance(CommentDAO.class);
		try {
			dao.remove(42L);
			fail();
		} catch (EntityNotFoundException e) {
			//success
		}
	}

	@Test
	public void testDelete() {
		CommentDAO dao = getInstance(CommentDAO.class);
		try {
			dao.remove(-111L);
		} catch (EntityNotFoundException e) {
			fail();
		}
		assertThat(dao.get(-111L)).isNull();
	}
	
	@Test
	public void testPage() {
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

	@Test
	public void testGetCommentsBy() {
		CommentDAO dao = getInstance(CommentDAO.class);

		Page<Comment> page = dao.getCommentsBy("facebook::testuser", 0, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(0);
		assertThat(page.getTotalPageCount()).isEqualTo(3);
		assertThat(page.getTotalRowCount()).isEqualTo(26);

		page = dao.getCommentsBy("facebook::testuser", 2, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageIndex()).isEqualTo(2);
		assertThat(page.getTotalPageCount()).isEqualTo(3);
		assertThat(page.getTotalRowCount()).isEqualTo(26);
	}
}
