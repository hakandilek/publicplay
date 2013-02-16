package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name="TBL_USER_FOLLOW")
@SuppressWarnings("serial")
public class UserFollow extends Model implements TimestampModel<UserFollowPK> {

	@EmbeddedId
	private UserFollowPK key;
    
	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;
	
	@Version
	private int revision;
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public UserFollowPK getKey() {
		return key;
	}

	public void setKey(UserFollowPK key) {
		this.key = key;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostRating [key=").append(key).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append("]");
		return builder.toString();
	}
}
