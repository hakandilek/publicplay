package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import karma.model.Impact;
import karma.model.Reputable;
import karma.model.ReputationEntry;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name = "TBL_REPUTATION")
@SuppressWarnings("serial")
public class Reputation extends Model implements TimestampModel<Long>,
		ReputationEntry {

	@Id
	private Long key;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;

	@Version
	private int revision;

	@Basic
	@Column(nullable = false)
	private String reputationValueKey;

	@Basic
	@Column(nullable = false)
	private String ownerKey;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public void setImpact(Impact impact) {
		reputationValueKey = impact.getKey();
	}

	public String getReputationValueKey() {
		return reputationValueKey;
	}

	public void setReputationValueKey(String reputationValueKey) {
		this.reputationValueKey = reputationValueKey;
	}

	public String getOwnerKey() {
		return ownerKey;
	}

	public void setOwnerKey(String ownerKey) {
		this.ownerKey = ownerKey;
	}

	public void setOwner(Reputable subject) {
		this.ownerKey = subject.getKey();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reputation [key=").append(key).append(", createdOn=")
				.append(createdOn).append(", updatedOn=").append(updatedOn)
				.append(", revision=").append(revision)
				.append(", reputationValueKey=").append(reputationValueKey)
				.append(", ownerKey=").append(ownerKey).append("]");
		return builder.toString();
	}
	
}