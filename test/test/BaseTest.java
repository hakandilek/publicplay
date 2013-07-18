package test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import play.test.FakeApplication;
import play.test.Helpers;
import plugins.GuicePlugin;
import plugins.S3Plugin;

import com.amazonaws.services.s3.AmazonS3Client;

public class BaseTest {

	protected FakeApplication app;

	protected <T> T mock(Class<T> type) {
		T m = Mockito.mock(type);
		return m;
	}

	protected <T> T getInstance(Class<T> type) {
		GuicePlugin guice = GuicePlugin.getInstance();
		T t = guice.getInstance(type);
		return t;
	}

	@Before
	public void startApp() {
		S3Plugin.amazonS3 = mock(AmazonS3Client.class);
		when(S3Plugin.amazonS3.doesBucketExist(anyString())).thenReturn(true);

		if (app == null)
			app = fakeApplication(inMemoryDatabase());
		
		Helpers.start(app);
	}

	@After
	public void stopApp() {
		Helpers.stop(app);
		app = null;
	}
}