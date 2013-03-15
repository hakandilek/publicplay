package plugins;

import module.Dependencies;
import play.Application;
import play.Logger;
import play.Logger.ALogger;
import play.Plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuicePlugin extends Plugin {

	private static ALogger log = Logger.of(GuicePlugin.class);

	private Injector injector = null;

	private static GuicePlugin instance;

	public GuicePlugin(Application app) {
		super();
	}

	public <T> T getInstance(Class<T> paramClass) {
		if (this.injector == null)
			log.warn("injector is null - perhaps plugin is not configured before GlobalPlugin or onStart was not called yet");

		return this.injector.getInstance(paramClass);
	}

	private static Injector createInjector() {
		return Guice.createInjector(new Dependencies());
	}

	public void onStart() {
		log.info("starting " + getClass().getSimpleName());
		injector = createInjector();
		log.info("Injector created.");
		instance = this;
	}

	public void onStop() {
		super.onStop();
		instance = null;
	}

	public static GuicePlugin getInstance() {
		return instance;
	}

}
