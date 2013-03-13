package security;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http;
import play.mvc.Result;
import views.html.security.DeadboltUtils;
import be.objectify.deadbolt.DeadboltHandler;
import be.objectify.deadbolt.actions.AbstractRestrictiveAction;
import be.objectify.deadbolt.core.models.Subject;

public class RestrictApprovedAction extends AbstractRestrictiveAction<RestrictApproved> {

	private static ALogger log = Logger.of(RestrictApprovedAction.class);

	@Override
	public Result applyRestriction(Http.Context ctx,
			DeadboltHandler deadboltHandler) throws Throwable {
		Result result;
		if (isAllowed(ctx, deadboltHandler)) {
			markActionAsAuthorised(ctx);
			result = delegate.call(ctx);
		} else {
			markActionAsUnauthorised(ctx);
			result = onAccessFailure(deadboltHandler, configuration.content(),
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

}