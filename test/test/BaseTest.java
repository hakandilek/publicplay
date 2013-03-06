package test;

import plugins.GuicePlugin;

public class BaseTest {

	protected <T> T getInstance(Class<T> type) {
		GuicePlugin guice = GuicePlugin.getInstance();
		T t = guice.getInstance(type);
		return t;
	}

}
