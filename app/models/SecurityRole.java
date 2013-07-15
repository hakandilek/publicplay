package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;
import be.objectify.deadbolt.core.models.Role;

@Entity
@Table(name="TBL_SECURITY_ROLE")
public class SecurityRole extends Model implements Role, BasicModel<Long> {

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

	public SecurityRole(Long key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	public SecurityRole() {
		super();
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecurityRole [").append(name).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityRole other = (SecurityRole) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}