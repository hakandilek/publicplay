package module;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import plugins.GuicePlugin;
import views.html.errors.onBadRequest;
import views.html.errors.onError;
import views.html.errors.onNotFound;

public class Global extends GlobalSettings {

	private Application app;

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
			throws Exception {
		return GuicePlugin.getInstance().getInstance(controllerClass);
	}

	public void onStart(Application app) {
		Logger.info("application starting:" + app);
		this.app = app;

	}

	@Override
	public Result onBadRequest(RequestHeader req, String error) {
		if (app.isProd()) {
			return badRequest(onBadRequest.render(req, error));
		}
		return super.onBadRequest(req, error);
	}

	@Override
	public Result onError(RequestHeader req, Throwable error) {
		if (app.isProd()) {
			return internalServerError(onError.render(error));
		}
		return super.onError(req, error);
	}

	@Override
	public Result onHandlerNotFound(RequestHeader req) {
		if (app.isProd()) {
			return notFound(onNotFound.render(req));
		}
		return super.onHandlerNotFound(req);
	}
}