package module;

import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.typesafe.plugin.inject.InjectPlugin;

import controllers.crud.CategoryController;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		InjectPlugin plugin = app.plugin(InjectPlugin.class);
		Logger.warn("getting an instance from guice:"
				+ plugin.getInstance(CategoryController.class));
	}
}