package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import org.junit.Test;

import play.mvc.Result;
import test.IntegrationTest;

public class PostControllerTest extends IntegrationTest {

	@Test
	public void testNewForm() {
		test(new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App.postNewForm());
				assertThat(status(result)).isEqualTo(SEE_OTHER);
			}
		});
	}

	@Test
	public void testEditForm() {
		test(new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App.postEditForm(-11l));
				assertThat(status(result)).isEqualTo(SEE_OTHER);
			}
		});
	}

	@Test
	public void testEditCommentForm() {
		test(new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App
						.commentEditForm(-11l, -111l));
				assertThat(status(result)).isEqualTo(SEE_OTHER);
			}
		});
	}

/*
	@Test
	public void testShow() {
		test(new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.App
						.postShow(-11l, "title", 0));
				assertThat(status(result)).isEqualTo(OK);
			}
		});
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
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
		test(new Runnable() {
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
*/
}
