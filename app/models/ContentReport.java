package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

import com.avaje.ebean.annotation.EnumValue;

@Entity
@Table(name = "TBL_CONTENT_REPORT")
@SuppressWarnings("serial")
public class ContentReport extends Model implements TimestampModel<Long>, Owned<Long> {

	public enum Status {
		@EnumValue("N")
		NEW, 
		@EnumValue("P")
		PROCESSED, 
		@EnumValue("I")
		IGNORED, 
	}

	public enum ContentType {
		@EnumValue("P")
		POST, 
		@EnumValue("C")
		COMMENT, 
	}

	public enum Reason {
		@EnumValue("E")
		EXPIRED, 
		@EnumValue("C")
		INCORRECT, 
		@EnumValue("I")
		INAPPROPRIATE, 
		@EnumValue("O")
		OTHER, 
	}

	@Id
	private Long key;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;
	
	@Version
	private int revision;

	@Required
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private User updatedBy;

	/** post or comment key */
	@Required
	@Basic
	private Long contentKey;
	
	@Column(length=512, nullable = true)
	private String comment;

    @Enumerated(value=EnumType.STRING)
    private Status status = Status.NEW;

    @Enumerated(value=EnumType.STRING)
    private ContentType contentType;

    @Enumerated(value=EnumType.STRING)
    private Reason reason;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

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

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getContentKey() {
		return contentKey;
	}

	public void setContentKey(Long contentKey) {
		this.contentKey = contentKey;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "ContentReport [key=" + key + 
				", contentType=" + contentType + 
				", contentKey=" + contentKey + 
				", status=" + status +
				", createdBy=" + createdBy + 
				"]";
	}

	
}
