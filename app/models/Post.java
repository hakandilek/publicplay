package models;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;

@Entity
@Table(name="TBL_POST")
@SuppressWarnings("serial")
public class Post extends Model implements TimestampModel<Long>, Owned<Long> {

	@Id
	private Long key;

	@Required
	@Column(length=512, nullable = true)
	@MaxLength(120)
	private String title;

	@Required
	@Column(length=2048, nullable = true)
	@MaxLength(2000)
	private String content;

	@Basic
	private Integer rating = 0;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;
	
	@Basic
	private Date approvedOn;
	
	@Version
	private int version;

	@Basic
	private String creatorIp;
	
	@Basic
	private String modifierIp;

	@OneToOne()
	private S3File image;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Comment> comments;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private User updatedBy;

	@ManyToOne
	@JoinColumn(name = "approved_by", nullable = true)
	private User approvedBy;

	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private Category category;

    @Enumerated(value=EnumType.STRING)
    private ContentStatus status = ContentStatus.NEW;

    @Transient
	private UUID imageKey;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public S3File getImage() {
		return image;
	}

	public void setImage(S3File image) {
		this.image = image;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getRevision() {
		return version;
	}

	public void setRevision(int revision) {
		this.version = revision;
	}
	
	public ContentStatus getStatus() {
		return status;
	}

	public void setStatus(ContentStatus status) {
		this.status = status;
	}

	public UUID getImageKey() {
		return imageKey;
	}

	public void setImageKey(UUID imageKey) {
		this.imageKey = imageKey;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [key=").append(key).append(", version=")
				.append(version).append(", title=").append(title)
				.append(", content=").append(content).append(", rating=")
				.append(rating).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn)
				.append(", creatorIp=").append(creatorIp)
				.append(", modifierIp=").append(modifierIp).append(", image=")
				.append(image).append(", comments=").append(comments)
				.append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append(", category=")
				.append(category).append("]");
		return builder.toString();
	}

}
