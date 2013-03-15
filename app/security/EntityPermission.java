package security;

import scala.collection.mutable.StringBuilder;
import be.objectify.deadbolt.core.models.Permission;

public class EntityPermission implements Permission {

	private final String value;

	public <T, K> EntityPermission(Class<T> entity, String action, K key) {
		this.value = new StringBuilder(entity.getName()).append('.')
				.append(action).append('.').append(key).toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		java.lang.StringBuilder builder = new java.lang.StringBuilder();
		builder.append("EntityPermission [").append(value).append("]");
		return builder.toString();
	}

	
}
