package securesocial.core.java;

public class LoginProvider {
	
	public String providerId;
	
	public String authenticationUrl;

	public LoginProvider() {
		super();
	}

	public LoginProvider(String providerId,
			String authenticationUrl) {
		super();
		this.providerId = providerId;
		this.authenticationUrl = authenticationUrl;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginProvider [providerId=").append(providerId)
				.append(", authenticationUrl=").append(authenticationUrl)
				.append("]");
		return builder.toString();
	}

}
