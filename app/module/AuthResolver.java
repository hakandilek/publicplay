package module;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate.Resolver;

import controllers.routes;

public class AuthResolver extends Resolver {

	private static ALogger log = Logger.of(AuthResolver.class);
	
	@Override
	public Call afterAuth() {
		if (log.isDebugEnabled())
			log.debug("afterAuth <-");
        // The user will be redirected to this page after authentication
        // if no original URL was saved
        return routes.App.index();
	}

	@Override
	public Call afterLogout() {
		if (log.isDebugEnabled())
			log.debug("afterLogout <-");
        return routes.App.index();
	}

	@Override
	public Call auth(String provider) {
		if (log.isDebugEnabled())
			log.debug("auth <-");
        return routes.App.authenticate(provider);
	}

	@Override
	public Call login() {
		if (log.isDebugEnabled())
			log.debug("login <-");
		return routes.App.login();
	}

    @Override
    public Call askLink() {
    	if (log.isDebugEnabled())
			log.debug("askLink <-");
        // We don't support moderated account linking in this sample.
        // See the play-authenticate-usage project for an example
        return null;
    }

    @Override
    public Call askMerge() {
    	if (log.isDebugEnabled())
			log.debug("askMerge <-");
        // We don't support moderated account merging in this sample.
        // See the play-authenticate-usage project for an example
        return null;
    }
}
