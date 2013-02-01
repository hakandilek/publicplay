package plugins;

import java.util.ArrayList;
import java.util.List;

import play.Application;
import play.Logger;
import play.Logger.ALogger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.typesafe.plugin.inject.InjectPlugin;
import com.typesafe.plugin.inject.RequestStaticInjection;

public class GuicePlugin extends InjectPlugin {

	private static ALogger log = Logger.of(GuicePlugin.class);

	private Injector injector = null;

	public GuicePlugin(Application app) {
		super(app);
	}

	public <T> T getInstance(Class<T> paramClass) {
		if (this.injector == null)
			log.warn("injector is null - perhaps plugin is not configured before GlobalPlugin or onStart was not called yet");

		return this.injector.getInstance(paramClass);
	}

	public void onStart() {
		log.info("starting " + getClass().getSimpleName());

		Class<Object>[] injectClasses = scanInjectableClasses();
		if (log.isDebugEnabled()) {
			log.debug("injectable classes:");
			for (Class<Object> class1 : injectClasses) {
				log.debug("  class : " + class1);
			}
		}
		
		List<Object> availableModules = availableModules();
		List<Module> modules = convertToModules(availableModules, injectClasses);
		this.injector = Guice.createInjector(modules);

		log.info("started " + getClass().getSimpleName());
	}

	private List<Module> convertToModules(List<Object> moduleList,
			Class<Object>[] paramArrayOfClass) {
		List<Module> list = new ArrayList<Module>();
		list.add(new RequestStaticInjection(paramArrayOfClass));
		for (Object module : moduleList) {
			log.info("include module : " + module);
			list.add((Module) module);
		}
		return list;
	}

}
