package module;

import models.SourceConfiguration;
import models.dao.SourceConfigurationDAO;

import com.google.inject.AbstractModule;
import com.pickleproject.Configuration;
import com.pickleproject.shopping.ConfigurationDAO;
import com.pickleproject.shopping.ProductDAOConfiguration;

public class Dependencies extends AbstractModule {

	@Override
	protected void configure() {
		SourceConfigurationDAO sourceConfigurationDAO = new SourceConfigurationDAO();
		bind(ConfigurationDAO.class).toInstance(sourceConfigurationDAO);
		bind(SourceConfigurationDAO.class).toInstance(sourceConfigurationDAO);
		bind(Configuration.class).toInstance(new ProductDAOConfiguration<Long, SourceConfiguration>(sourceConfigurationDAO));
	}


}