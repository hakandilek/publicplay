package models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PostRatingPK implements Serializable {

	/** serial id */
	private static final long serialVersionUID = 1L;

	public String userKey;

	public Long postKey;

	public PostRatingPK(String userKey, Long postKey) {
		super();
		this.userKey = userKey;
		this.postKey = postKey;
	}

	public PostRatingPK() {
		super();
	}
	
	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return userKey;
	}

	/**
	 * @param userKey the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the postKey
	 */
	public Long getPostKey() {
		return postKey;
	}

	/**
	 * @param postKey the postKey to set
	 */
	public void setPostKey(Long postKey) {
		this.postKey = postKey;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((postKey == null) ? 0 : postKey.hashCode());
		result = prime * result + ((userKey == null) ? 0 : userKey.hashCode());
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostRatingPK other = (PostRatingPK) obj;
		if (postKey == null) {
			if (other.postKey != null)
				return false;
		} else if (!postKey.equals(other.postKey))
			return false;
		if (userKey == null) {
			if (other.userKey != null)
				return false;
		} else if (!userKey.equals(other.userKey))
			return false;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[userKey=").append(userKey)
				.append(", postKey=").append(postKey).append("]");
		return builder.toString();
	}

	public static PostRatingPK fromString(String string) {
		if (string == null || "".equals(string) || string.indexOf('.') > 1)
			return null;
		String[] parts = string.split("\\.");
		String uk = parts[0];
		Long pk = Long.parseLong(parts[1], 10);
		return new PostRatingPK(uk, pk);
	}
}
