package securesocial.core.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scala.collection.Iterable;
import scala.collection.Iterator;
import securesocial.core.IdentityProvider;
import securesocial.core.ProviderRegistry;

public class LoginProviderRegistry {
	private static List<LoginProvider> loginProviders;

	public static List<LoginProvider> getLoginProviders() {
		if (loginProviders == null) {
			loginProviders = new ArrayList<LoginProvider>();
			Iterable<IdentityProvider> values = ProviderRegistry.all().values();
			Iterator<IdentityProvider> iterator = values.iterator();
			while (iterator.hasNext()) {
				IdentityProvider provider = iterator.next();
				String providerId = provider.providerId();
				String authenticationUrl = provider.authenticationUrl();
				loginProviders.add(new LoginProvider(providerId, authenticationUrl));
			}
		}
		return loginProviders;
	}

}
