package module;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import play.Application;
import play.Logger;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.utils.crud.GlobalCRUDSettings;
import plugins.GuicePlugin;
import views.html.errors.onBadRequest;
import views.html.errors.onError;
import views.html.errors.onNotFound;

import com.feth.play.module.pa.PlayAuthenticate;

public class Global extends GlobalCRUDSettings {

	private Application app;

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
			throws Exception {
		return GuicePlugin.getInstance().getInstance(controllerClass);
	}

	public void onStart(Application app) {
		Logger.info("application starting:" + app);
		super.onStart(app);
		this.app = app;
		PlayAuthenticate.setResolver(new AuthResolver());
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