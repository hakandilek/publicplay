package controllers;

import static org.junit.Assert.*;

import models.User;

import org.junit.Test;

import test.IntegrationTest;

public class HttpUtilsTest extends IntegrationTest {

	@Test
	public void testLoginUser() {
		login("testuser");
		User user = HttpUtils.loginUser();
		assertNotNull(user);
	}

	@Test
	public void testIsAdmin() {
		assertFalse(HttpUtils.isAdmin());
		login("testuser");
		assertFalse(HttpUtils.isAdmin());
		login("807220003");
		assertTrue(HttpUtils.isAdmin());
	}

}
