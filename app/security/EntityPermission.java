package security;

import scala.collection.mutable.StringBuilder;
import be.objectify.deadbolt.models.Permission;

public abstract class EntityPermission<T, K> implements Permission {

	private final String value;

	public EntityPermission(Class<T> entity, String action, K key) {
		this.value = new StringBuilder(entity.getName()).append('.')
				.append(action).append('.').append(key).toString();
	}

	@Override
	public String getValue() {
		return value;
	}

}
