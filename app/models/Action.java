package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name="TBL_REPUTATION")
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
    @JoinColumn(name="postKey", nullable=false)
	private Post post;
	
	@OneToOne
	@Column(name="COMMENT_KEY")
    private Comment comment;
	
	@OneToOne
    private User actionUser;
	
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void setUser(User user) {
		actionUser=user;
	}
	public User getUser() {
		return actionUser;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [key=").append(key)
				.append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn)
				.append(", post=").append(post)
				.append(", createdBy=").append(createdBy)
				.append(", revision=").append(revision).append("]");
		return builder.toString();
	}
}