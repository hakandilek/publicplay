package test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import play.Logger;
import play.Logger.ALogger;
import play.api.mvc.RequestHeader;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import play.utils.dao.EntityNotFoundException;

public class IntegrationTest extends BaseTest {

	private static ALogger log = Logger.of(IntegrationTest.class);

	private String userLogin;

	private IntegrationTestHelper testHelper = new IntegrationTestHelper();

	@Before
	public void start() {
		app = fakeApplication(inMemoryDatabase());
		testHelper.reset(app);
		resetHttpContext(null);
	}

	@After
	public void stop() throws EntityNotFoundException {
		userLogin = null;
	}

	protected void login(String username) {
		login(username, null);
	}

	protected void login(String username, Map<String, String[]> requestBody) {
		if (log.isDebugEnabled())
			log.debug("username : " + username);

		userLogin = username;
		resetHttpContext(requestBody);
	}

	protected void resetHttpContext(Map<String, String[]> requestBody) {
		RequestHeader reqHeader = mock(RequestHeader.class);
		Request req = mock(Request.class);
		if (requestBody != null) {
			RequestBody reqBody = mock(RequestBody.class);
			when(req.body()).thenReturn(reqBody);
			when(reqBody.asFormUrlEncoded()).thenReturn(requestBody);
		}
		Map<String, String> session = new HashMap<String, String>();
		Map<String, String> flash = new HashMap<String, String>();
		Map<String, Object> args = new HashMap<String, Object>();

		if (log.isDebugEnabled())
			log.debug("userLogin : " + userLogin);

		if (userLogin != null) {
			session.put("pa.p.id", "facebook");
			session.put("pa.u.id", userLogin);
		}

		Context.current.set(new Context(1L, reqHeader, req, session, flash, args));
	}
}
