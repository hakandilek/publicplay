package models.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.ContentReport;
import models.ContentReport.ContentType;

import org.junit.Test;

import test.IntegrationTest;

public class ContentReportDAOTest extends IntegrationTest {

	@Test
	public void findForContent() {
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

}
