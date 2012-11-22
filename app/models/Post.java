package models;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.joda.time.DateTime;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.cache.CachedFinder;
import play.utils.cache.InterimCache;

import com.avaje.ebean.Page;

@Entity
@Table(name="TBL_POST")
@SuppressWarnings("serial")
public class Post extends Model {

	private static final int TOP_10 = 10;

	@Id
	private Long key;

	@Required
	@Column(length=512, nullable = true)
	private String title;

	@Required
	@Column(length=2048, nullable = true)
	private String content;

	@Basic
	private Integer rating = 0;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;

	@OneToOne()
	private S3File image;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Comment> comments;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Version
	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	private User updatedBy;

	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private Category category;

	public static CachedFinder<Long, Post> find = new CachedFinder<Long, Post>(
			Long.class, Post.class);

	public static InterimCache<Post> cache = new InterimCache<Post>(Post.class,
			3600);

	public static List<Post> all() {
		return find.all();
	}

	/**
	 * Return a page of posts
	 * 
	 * @param page
	 *            Page to display
	 * @param pageSize
	 *            Number of computers per page
	 */
	public static Page<Post> page(int page, int pageSize) {
		return find.page(page, pageSize, "createdOn desc");
	}

	public static Page<Post> topDayPage() {
		return cache.page(".topDay", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				DateTime now = new DateTime();
				DateTime yesterday = now.minusDays(1);
				return find.where().gt("createdOn", yesterday.toDate())
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public static Page<Post> topWeekPage() {
		return cache.page(".topWeek", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				DateTime now = new DateTime();
				DateTime lastWeek = now.minusDays(7);
				return find.where().gt("createdOn", lastWeek.toDate())
						.orderBy("rating desc").findPagingList(TOP_10)
						.getPage(0);
			}
		});
	}

	public static Page<Post> topAllPage() {
		return cache.page(".topAll", new Callable<Page<Post>>() {
			public Page<Post> call() throws Exception {
				return find.where().orderBy("rating desc")
						.findPagingList(TOP_10).getPage(0);
			}
		});
	}

	public static void create(Post post) {
		Date now = new Date();
		post.setCreatedOn(now);
		post.setUpdatedOn(now);
		post.save();
		find.put(post.getKey(), post);
		// clean user cache for a backlink update
		User owner = post.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
	}

	public static void remove(Long key) {
		Post post = find.ref(key);
		post.delete();
		find.clean(key);
		// clean user cache for a backlink update
		User owner = post.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
	}

	public static Post get(Long key) {
		return find.byId(key);
	}

	public static void update(Long key, Post post) {
		Date now = new Date();
		post.setUpdatedOn(now);
		post.update(key);
		find.put(post.getKey(), post);
		// clean user cache for a backlink update
		User owner = post.createdBy;
		if (owner != null)
			User.find.clean(owner.getKey());
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [key=").append(key).append(", rating=")
				.append(rating).append(", title=").append(title)
				.append(", content=").append(content).append("]");
		return builder.toString();
	}

}
