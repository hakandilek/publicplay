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

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name="TBL_COMMENT")
@SuppressWarnings("serial")
public class Comment extends Model implements TimestampModel<Long>, Owned<Long>, ActionSubject {

	@Id
	private Long key;

    @Required
    @Column(length=2048, nullable = true)
    @MaxLength(2000)
	private String content;
	
    @Basic
    private Date createdOn;

    @Basic
    private Date updatedOn;
    
    @Basic
    private Date approvedOn;
    
    @Basic
	private String creatorIp;
	
	@Version
	private int revision;

	@Basic
	private String modifierIp;
	
    @ManyToOne
    @JoinColumn(name="postKey", nullable=false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="updated_by", nullable=true)
    private User updatedBy;

	@ManyToOne
	@JoinColumn(name = "approved_by", nullable = true)
	private User approvedBy;

    @Enumerated(value=EnumType.STRING)
    private ContentStatus status = ContentStatus.NEW;

	@Override
	public void associate(Action action) {
		action.setTargetComment(this);
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCreatorIp() {
		return creatorIp;
	}
	
	public void setCreatorIp(String ipToSet) {
		this.creatorIp = ipToSet;
	}
	
	public String getModifierIp() {
		return modifierIp;
	}
	
	public void setModifierIp(String ipToSet) {
		this.modifierIp=ipToSet;
	}
	
	public ContentStatus getStatus() {
		return status;
	}

	public void setStatus(ContentStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [key=").append(key).append(", content=")
				.append(content).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn)
				.append(", creatorIp=").append(creatorIp)
				.append(", modifierIp=").append(modifierIp).append(", post=")
				.append(post).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy)
				.append(", revision=").append(revision).append("]");
		return builder.toString();
	}


}
