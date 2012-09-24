package test;

import static org.junit.Assert.*;

import models.Post;

import org.junit.*;

import play.mvc.*;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class PostControllerTest {

	@Test
	public void findById() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Post post = Post.find.byId(-11l);
				assertThat(post).isNotNull();
			}
		});
	}

	@Test
	public void testNewForm() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				Result result = callAction(controllers.routes.ref.PostController
						.newForm());
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
				Result result = callAction(controllers.routes.ref.PostController
						.editForm(-11l));
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
				Result result = callAction(controllers.routes.ref.PostController
						.show(-11l, "title", 0));
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
				Result result = callAction(controllers.routes.ref.PostController
						.editCommentForm(-11l, -111l));
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
