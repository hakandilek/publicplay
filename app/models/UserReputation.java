package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import karma.model.ReputationHolder;

import play.db.ebean.Model;
import play.utils.dao.BasicModel;

@Entity
@Table(name="TBL_USERREPUTATION")
@SuppressWarnings("serial")
public class UserReputation extends Model implements BasicModel<Long>, ReputationHolder {

	@Id
	private Long key;
	
	@Version
	private int revision;
	
	@Basic
	private Integer value;
	
	@OneToOne
	private User targetUser;
	
	public UserReputation(User targetUser, int value) {
		this.targetUser = targetUser;
		this.value = value;
	}

	public UserReputation() {
		super();
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserReputation [key=").append(key)
				.append(", revision=").append(revision).append(", value=")
				.append(value).append(", targetUser=").append(targetUser)
				.append("]");
		return builder.toString();
	}

	@Override
	public void increase(Number v) {
		value += v.intValue();
	}

}