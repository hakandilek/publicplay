package models.dao;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static org.fest.assertions.Assertions.assertThat;
import models.SourceConfiguration;

import org.junit.Test;

import play.utils.dao.EntityNotFoundException;

import test.BaseTest;

public class SourceConfigurationDAOTest extends BaseTest{

	public SourceConfigurationDAOTest() {
		super();
	}
	
	@Test
	public void createAndRemoveSourceConfigurationSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				SourceConfigurationDAO sourceConfigurationDAO=getInstance(SourceConfigurationDAO.class);
				
				SourceConfiguration sourceConfiguration=new SourceConfiguration();
				sourceConfiguration.setSourceKey("sourceKey");
				
				
				Long key =sourceConfigurationDAO.create(sourceConfiguration);
				assertThat(sourceConfigurationDAO.getWithSourceKey("sourceKey")).isEqualTo(sourceConfiguration);
				try {
					sourceConfigurationDAO.remove(key);
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				assertThat(sourceConfigurationDAO.getWithSourceKey("sourceKey")).isNull();
			}
		}); 
	}
	
	@Test
	public void updateSourceConfigurationSucceeds() {
		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			public void run() {
				SourceConfigurationDAO sourceConfigurationDAO=getInstance(SourceConfigurationDAO.class);
				
				SourceConfiguration sourceConfiguration=new SourceConfiguration();
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
