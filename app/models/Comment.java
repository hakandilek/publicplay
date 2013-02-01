package models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.cache.CachedFinder;
import play.utils.dao.TimestampModel;

import com.avaje.ebean.Page;

@Entity
@Table(name="TBL_COMMENT")
@SuppressWarnings("serial")
public class Comment extends Model implements TimestampModel<Long>{

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

    public static CachedFinder<Long, Comment> find = new CachedFinder<Long, Comment>(Long.class, Comment.class);

	/**
	 * Return a page of comments
	 * 
	 * @param postKey
	 *            post key
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 */
	public static Page<Comment> page(Long postKey, int page, int pageSize) {
		return find.page(page, pageSize, "createdOn desc", "postKey", postKey);
	}
   
	public static void create(Comment comment) {
		Date now = new Date();
		comment.setCreatedOn(now);
		comment.setUpdatedOn(now);
		comment.save();
		find.put(comment.getKey(), comment);
		// clean user cache for a backlink update
		User owner = comment.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
	}

	public static void remove(Long key) {
		Comment comment = find.ref(key);
		comment.delete();
		find.clean(key);
		// clean user cache for a backlink update
		User owner = comment.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
	}

	public static Comment get(Long key) {
		return find.byId(key);
	}

	public static void update(Long key, Comment comment) {
		Date now = new Date();
		comment.setUpdatedOn(now);
		comment.update(key);
		find.put(comment.getKey(), comment);
		// clean user cache for a backlink update
		User owner = comment.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [key=").append(key).append(", content=")
				.append(content).append(", createdOn=").append(createdOn)
				.append(", updatedOn=").append(updatedOn)
				.append(", creatorIp=").append(creatorIp)
				.append(", modifierIp=").append(modifierIp).append(", post=")
				.append(post).append(", createdBy=").append(createdBy)
				.append(", updatedBy=").append(updatedBy).append("]");
		return builder.toString();
	}


}
