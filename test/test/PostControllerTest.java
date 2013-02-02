package test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;

public class PostControllerTest {

	@Test
	public void testNewForm() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App.postNewForm());
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("new post");
			}
		});
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditForm() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App.postEditForm(-11l));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("update post");
			}
		});
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testShow() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App
						.postShow(-11l, "title", 0));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("show");
			}
		});
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditCommentForm() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App.commentEditForm(-11l, -111l));
				assertThat(status(result)).isEqualTo(OK);
				assertThat(contentType(result)).isEqualTo("text/html");
				assertThat(charset(result)).isEqualTo("utf-8");
				assertThat(contentAsString(result)).contains("some comment</textarea>");
			}
		});
	}

	@Test
	public void testUpdateComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteComment() {
		fail("Not yet implemented");
	}

}
