package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import test.IntegrationTest;

public class CommentControllerTest extends IntegrationTest {


	@Test
	public void testDelete() {
		Result result;
		result = getInstance(CommentController.class).delete(-11L, -111L);
		assertThat(status(result)).isEqualTo(SEE_OTHER);

		result = getInstance(CommentController.class).delete(-11L, 42L);
		assertThat(status(result)).isEqualTo(NOT_FOUND);
	}

	@Test
	public void testEditForm() {
		Result result;

		result = getInstance(CommentController.class).editForm(-11L, -111L);
		assertThat(status(result)).isEqualTo(NOT_FOUND);

		login("testuser");
		result = getInstance(CommentController.class).editForm(-11L, -111L);
		assertThat(status(result)).isEqualTo(OK);
	}

	/*
	@Test
	public void testUpdate() {
		Result result;

		// TODO: use http POST form data
		result = getInstance(CommentController.class).update(-11L, 42L);
		assertThat(status(result)).isEqualTo(NOT_FOUND);

		login("testuser");
		result = getInstance(CommentController.class).update(-11L, -111L);
		assertThat(status(result)).isEqualTo(OK);
	}
	/*/
}
