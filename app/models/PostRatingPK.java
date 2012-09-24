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

}
