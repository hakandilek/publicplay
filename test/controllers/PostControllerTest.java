package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import test.IntegrationTest;

public class PostControllerTest extends IntegrationTest {
	@Test
	public void testNewForm() {
		Result result = getInstance(PostController.class).newForm();
		assertThat(status(result)).isEqualTo(OK);
	}

	@Test
	public void testEditForm() {
		Result result = getInstance(PostController.class).editForm(-11L);
		assertThat(status(result)).isEqualTo(OK);
	}

	@Test
	public void testShow() {
		Result result;

		result = getInstance(PostController.class).show(-11L, "title", 0);
		assertThat(status(result)).isEqualTo(OK);

		result = getInstance(PostController.class).show(42L, "not found", 0);
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testDelete() {
		Result result;

		result = getInstance(PostController.class).delete(-11L);
		assertThat(status(result)).isEqualTo(SEE_OTHER);

		result = getInstance(PostController.class).delete(42L);
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testListFollowing() {
		Result result;
		result = getInstance(PostController.class).listFollowing(0);
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testUpdateAnonymous() {
		Result result;

		result = getInstance(PostController.class).update(42L);
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}
	/*
	 * TODO: use http POST form data
	 * 
	 * @Test public void testUpdate() { Result result; HashMap<String, String[]>
	 * request = Maps.newHashMap(); request.put("title", new String[] { "test"
	 * }); login("testuser", request); result =
	 * getInstance(PostController.class).update(-11L);
	 * assertThat(status(result)).isEqualTo(OK); }
	 */
}
