package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import test.IntegrationTest;

public class HomeControllerTest extends IntegrationTest {

	@Test
	public void testIndex() {
		Result result;
		
		result = getInstance(App.class).index();
		assertThat(status(result)).isEqualTo(OK);
		
		login("testuser");
		result = getInstance(App.class).index();
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
		assertThat(contentAsString(result)).contains("home");
	}

}
