package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name="TBL_POST_RATING")
@SuppressWarnings("serial")
public class PostRating extends Model implements TimestampModel<PostRatingPK> {

	@EmbeddedId
	private PostRatingPK key;
    
	@Required
	private int value;

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
	
	public PostRatingPK getKey() {
		return key;
	}

	public void setKey(PostRatingPK key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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
		builder.append("PostRating [key=").append(key).append(", value=")
				.append(value).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn).append("]");
		return builder.toString();
	}
}
