package module;

import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.typesafe.plugin.inject.InjectPlugin;

import controllers.crud.CategoryCRUDController;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;

import static play.mvc.Results.*;

import views.html.errors.onNotFound;
import views.html.errors.onError;
import views.html.errors.onBadRequest;

public class Global extends GlobalSettings {

	private Application app;

	public void onStart(Application app) {
		Logger.info("application starting:"+app);
		this.app = app;

		InjectPlugin plugin = app.plugin(InjectPlugin.class);
		Logger.warn("getting an instance from guice:"
				+ plugin.getInstance(CategoryCRUDController.class));
	}

	
	@Override
	public Result onBadRequest(RequestHeader req, String error) {
		if (app.isProd())  {
			return badRequest(onBadRequest.render(req, error));
		}
		return super.onBadRequest(req, error);
	}
	
	@Override
	public Result onError(RequestHeader req, Throwable error) {
		if (app.isProd())  {
			return internalServerError(onError.render(error));
		}
		return super.onError(req, error);
	}

	@Override
	public Result onHandlerNotFound(RequestHeader req) {
//		if (app.isProd())  {
//			return notFound(onNotFound.render(req));
//		}
		return notFound(onNotFound.render(req));
//		return super.onHandlerNotFound(req);
	}
	
	
}