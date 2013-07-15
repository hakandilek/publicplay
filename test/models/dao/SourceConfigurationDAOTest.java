package models.dao;

import static org.fest.assertions.Assertions.assertThat;
import models.SourceConfiguration;

import org.junit.Test;

import play.utils.dao.EntityNotFoundException;
import test.IntegrationTest;

public class SourceConfigurationDAOTest extends IntegrationTest {

	@Test
	public void createAndRemoveSourceConfigurationSucceeds() {
		test(new Runnable() {
			public void run() {
				SourceConfigurationDAO sourceConfigurationDAO = getInstance(SourceConfigurationDAO.class);

				SourceConfiguration sourceConfiguration = new SourceConfiguration();
				sourceConfiguration.setSourceKey("sourceKey");

				Long key = sourceConfigurationDAO.create(sourceConfiguration);
				assertThat(sourceConfigurationDAO.getWithSourceKey("sourceKey")).isEqualTo(sourceConfiguration);
				try {
					sourceConfigurationDAO.remove(key);
				} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}
				assertThat(sourceConfigurationDAO.getWithSourceKey("sourceKey")).isNull();
			}
		});
	}

	@Test
	public void updateSourceConfigurationSucceeds() {
		test(new Runnable() {
			public void run() {
				SourceConfigurationDAO sourceConfigurationDAO = getInstance(SourceConfigurationDAO.class);

				SourceConfiguration sourceConfiguration = new SourceConfiguration();
				sourceConfiguration.setSourceKey("sourceKey");

				sourceConfigurationDAO.create(sourceConfiguration);
				assertThat(sourceConfigurationDAO.getWithSourceKey("sourceKey")).isEqualTo(sourceConfiguration);
				sourceConfiguration.setSourceKey("newSourceKey");
				sourceConfigurationDAO.update(sourceConfiguration);
				assertThat(sourceConfigurationDAO.getWithSourceKey("newSourceKey")).isEqualTo(sourceConfiguration);
			}
		});
	}
}
