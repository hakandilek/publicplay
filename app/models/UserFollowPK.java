package models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserFollowPK implements Serializable {

	/** serial id */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "sourceKey", nullable = false)
	private String sourceKey;

	@ManyToOne
	@JoinColumn(name = "targetKey", nullable = false)
	private String targetKey;


	public UserFollowPK(String sourceKey, String targetKey) {
		super();
		this.sourceKey = sourceKey;
		this.targetKey = targetKey;
	}

	public UserFollowPK() {
		super();
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public String getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserFollowPK [sourceKey=").append(sourceKey)
				.append(", targetKey=").append(targetKey).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sourceKey == null) ? 0 : sourceKey.hashCode());
		result = prime * result
				+ ((targetKey == null) ? 0 : targetKey.hashCode());
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
		UserFollowPK other = (UserFollowPK) obj;
		if (sourceKey == null) {
			if (other.sourceKey != null)
				return false;
		} else if (!sourceKey.equals(other.sourceKey))
			return false;
		if (targetKey == null) {
			if (other.targetKey != null)
				return false;
		} else if (!targetKey.equals(other.targetKey))
			return false;
		return true;
	}
	
	public String keyString() {
		return String.format("%s.%s", sourceKey, targetKey);
	}
	
	public static UserFollowPK fromString(String string) {
		if (string == null || "".equals(string) || string.indexOf('.') < 0)
			return null;
		String[] parts = string.split("\\.");
		String sourceKey = parts[0];
		String targetKey = parts[1];
		return new UserFollowPK(sourceKey, targetKey);
	}


}
