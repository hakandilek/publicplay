package models;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.cache.CachedFinder;
import be.objectify.deadbolt.models.Role;

@Entity
public class SecurityRole extends Model implements Role {

	/** serial id */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long key;

	@Basic
	@Required
    @MinLength(3)
    @MaxLength(50)
    @Column(length=50, unique=true)
	public String name;

    public SecurityRole(String name) {
		super();
		this.name = name;
	}

	public SecurityRole() {
		super();
	}

	public static final CachedFinder<String, SecurityRole> find = new CachedFinder<String, SecurityRole>(
			String.class, SecurityRole.class);

	public String getRoleName() {
		return name;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static List<SecurityRole> all() {
		return find.all();
	}

	public static void create(SecurityRole role) {
		role.save();
		find.put(role.getName(), role);
	}

	public static void remove(String key) {
		find.ref(key).delete();
		find.clean(key);
	}

	public static SecurityRole get(String key) {
		return find.byId(key);
	}

	public static void update(String key, SecurityRole role) {
		role.update(key);
		find.put(role.getName(), role);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecurityRole [").append(name).append("]");
		return builder.toString();
	}
	
	

}