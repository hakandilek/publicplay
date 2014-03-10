package module;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.notFound;
import play.Application;
import play.Logger;
import play.libs.F;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;
import play.utils.crud.GlobalCRUDSettings;
import plugins.GuicePlugin;
import views.html.errors.onBadRequest;
import views.html.errors.onError;
import views.html.errors.onNotFound;

import com.feth.play.module.pa.PlayAuthenticate;
import play.libs.F.*;

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
	public F.Promise<SimpleResult> onBadRequest(final RequestHeader req, final String error) {
		if (app.isProd()) {
			return F.Promise.promise(new Function0<SimpleResult>() {
	            @Override
	            public SimpleResult apply() throws Throwable {
	    			return badRequest(onBadRequest.render(req, error));
	            }
			});
		}
		return super.onBadRequest(req, error);
	}

	@Override
	public F.Promise<SimpleResult> onError(RequestHeader req, final Throwable error) {
		if (app.isProd()) {
			return F.Promise.promise(new Function0<SimpleResult>() {
	            @Override
	            public SimpleResult apply() throws Throwable {
	    			return internalServerError(onError.render(error));
	            }
			});
		}
		return super.onError(req, error);
	}

	@Override
	public F.Promise<SimpleResult> onHandlerNotFound(final RequestHeader req) {
		if (app.isProd()) {
			return F.Promise.promise(new Function0<SimpleResult>() {
	            @Override
	            public SimpleResult apply() throws Throwable {
	    			return notFound(onNotFound.render(req));
	            }
			});
		}
		return super.onHandlerNotFound(req);
	}
}