package test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.List;

import models.ContentReport;
import models.ContentReport.ContentType;
import models.dao.ContentReportDAO;

import org.junit.Test;

public class ContentReportDAOTest extends BaseTest {

	public ContentReportDAOTest() {
		super();
	}

	@Test
	public void findForContent() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				ContentReportDAO dao = getInstance(ContentReportDAO.class);

				List<ContentReport> reports = dao.findForContent(ContentType.POST, -11L);
				assertThat(reports).isNotNull();
				assertThat(reports.size()).isEqualTo(2);
				
				reports = dao.findForContent(ContentType.POST, -666L);
				assertThat(reports).isNotNull();
				assertThat(reports.size()).isEqualTo(0);
				
				reports = dao.findForContent(ContentType.COMMENT, -111L);
				assertThat(reports).isNotNull();
				assertThat(reports.size()).isEqualTo(2);
				
				reports = dao.findForContent(ContentType.COMMENT, -666L);
				assertThat(reports).isNotNull();
				assertThat(reports.size()).isEqualTo(0);
			}
		});
	}

}
