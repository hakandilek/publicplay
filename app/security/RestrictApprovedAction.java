package security;

import play.Logger;
import play.Logger.ALogger;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.SimpleResult;
import views.html.security.DeadboltUtils;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.actions.AbstractRestrictiveAction;
import be.objectify.deadbolt.core.models.Subject;

public class RestrictApprovedAction extends AbstractRestrictiveAction<RestrictApproved> {

	private static ALogger log = Logger.of(RestrictApprovedAction.class);

	@Override
	public F.Promise<SimpleResult>  applyRestriction(Http.Context ctx,
			DeadboltHandler deadboltHandler) throws Throwable {
		
		Promise<SimpleResult> result;
		if (isAllowed(ctx, deadboltHandler)) {
			markActionAsAuthorised(ctx);
			result = delegate.call(ctx);
		} else {
			markActionAsUnauthorised(ctx);
			result = onAuthFailure(deadboltHandler, configuration.content(),
					ctx);
		}

		return result;
	}

	private boolean isAllowed(Http.Context ctx, DeadboltHandler deadboltHandler) {
		if (log.isDebugEnabled())
			log.debug("isAllowed() <-");
		Subject roleHolder = getSubject(ctx, deadboltHandler);
		if (log.isDebugEnabled())
			log.debug("roleHolder : " + roleHolder);
		boolean approved = DeadboltUtils.isSubjectApproved(roleHolder);
		if (log.isDebugEnabled())
			log.debug("approved : " + approved);
		return approved;
	}

	@Override
	public Class<? extends DeadboltHandler> getDeadboltHandlerClass() {
		return configuration.handler();
	}

	@Override
	public String getHandlerKey() {
		return null;
	}

}