package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name="TBL_ACTION")
@SuppressWarnings("serial")
public class Action extends Model implements TimestampModel<Long> {

	@Id
	private Long key;
	
	@Basic
	private String name;
	
	@Basic
	private Date createdOn;
	
	@Basic
	private Date updatedOn;
	
	@Version
	private int revision;
	
	@OneToOne
	private Post targetPost;
	
	@OneToOne
    private Comment targetComment;
	
	@OneToOne
    private User targetUser;
	
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Post getTargetPost() {
		return targetPost;
	}

	public void setTargetPost(Post targetPost) {
		this.targetPost = targetPost;
	}

	public Comment getTargetComment() {
		return targetComment;
	}

	public void setTargetComment(Comment targetComment) {
		this.targetComment = targetComment;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public String toString() {
		return "Action [key=" + key + ", name=" + name + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", revision="
				+ revision + ", targetPost=" + targetPost + ", targetComment="
				+ targetComment + ", targetUser=" + targetUser + ", createdBy="
				+ createdBy + "]";
	}

}