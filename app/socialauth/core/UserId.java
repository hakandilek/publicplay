package socialauth.core;

import org.brickred.socialauth.Profile;

public class UserId {

	private String providerId;
	private String validatedId;

	public UserId(Profile user) {
		this(user.getProviderId(), user.getValidatedId());
	}

	public UserId(String providerId, String validatedId) {
		this.providerId = providerId;
		this.validatedId = validatedId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getValidatedId() {
		return validatedId;
	}

	public void setValidatedId(String validatedId) {
		this.validatedId = validatedId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((providerId == null) ? 0 : providerId.hashCode());
		result = prime * result
				+ ((validatedId == null) ? 0 : validatedId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserId other = (UserId) obj;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
			return false;
		if (validatedId == null) {
			if (other.validatedId != null)
				return false;
		} else if (!validatedId.equals(other.validatedId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserId [providerId=").append(providerId)
				.append(", validatedId=").append(validatedId).append("]");
		return builder.toString();
	}

}
