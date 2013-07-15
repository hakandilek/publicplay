package test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3Client;

import plugins.GuicePlugin;
import plugins.S3Plugin;

public class BaseTest {

	protected <T> T mock(Class<T> type) {
		T m = Mockito.mock(type);
		return m;
	}

	protected <T> T getInstance(Class<T> type) {
		GuicePlugin guice = GuicePlugin.getInstance();
		T t = guice.getInstance(type);
		return t;
	}

	protected void test(Runnable... testRuns) {
		S3Plugin.amazonS3 = mock(AmazonS3Client.class);
		when(S3Plugin.amazonS3.doesBucketExist(anyString())).thenReturn(true);
		
		running(fakeApplication(inMemoryDatabase()), new LinearRunnable(testRuns));
	}

}