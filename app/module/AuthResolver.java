package module;

import play.mvc.Call;

import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import controllers.routes;

public class AuthResolver extends Resolver {

	@Override
	public Call afterAuth() {
        // The user will be redirected to this page after authentication
        // if no original URL was saved
        return routes.App.index();
	}

	@Override
	public Call afterLogout() {
        return routes.App.index();
	}

	@Override
	public Call auth(String provider) {
        return routes.App.authenticate(provider);
	}

	@Override
	public Call login() {
		return routes.App.login();
	}

    @Override
    public Call askLink() {
        // We don't support moderated account linking in this sample.
        // See the play-authenticate-usage project for an example
        return null;
    }

    @Override
    public Call askMerge() {
        // We don't support moderated account merging in this sample.
        // See the play-authenticate-usage project for an example
        return null;
    }
}
