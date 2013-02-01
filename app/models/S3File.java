package models;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.Logger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.cache.CachedFinder;
import play.utils.dao.TimestampModel;
import plugins.S3Plugin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Entity
@Table(name="TBL_S3FILE")
@SuppressWarnings("serial")
public class S3File extends Model implements TimestampModel<UUID> {

	@Id
    public UUID id;

	@Required
    private String bucket;

	@Basic
    public String parent;

	@Required
    public String name;

	@Basic
	private Date createdOn;

	@Basic
	private Date updatedOn;
	
	@Version
	private int revision;


    @Transient
    public File file;

	public static CachedFinder<UUID, S3File> find = new CachedFinder<UUID, S3File>(
			UUID.class, S3File.class);

	
	@Override
	public UUID getKey() {
		return id;
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

	public static List<S3File> all() {
		return find.all();
	}

	public static void create(S3File c) {
		c.save();
		find.put(c.id, c);
	}

	public static void remove(UUID key) {
		find.ref(key).delete();
		find.clean(key);
	}

	public static S3File get(UUID key) {
		return find.byId(key);
	}

	public static void update(UUID key, S3File newFile) {
		remove(key);
		create(newFile);
		find.put(newFile.id, newFile);
	}

    public URL getUrl() {
		StringBuilder sb = new StringBuilder("http://").append(bucket)
				.append(".s3.amazonaws.com/").append(getActualFileName());
		try {
			return new URL(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
    }

    private String getActualFileName() {
		StringBuilder sb = new StringBuilder();
		if (parent != null && !"".equals(parent)) {
			sb.append(parent).append("/");
		}
		sb.append(id).append("/").append(name);
		return sb.toString();
    }

    @Override
    public void save() {
        AmazonS3 amazonS3 = S3Plugin.amazonS3;
		if (amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.s3Bucket;
            
            super.save(); // assigns an id

            String filename = getActualFileName();
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        AmazonS3 amazonS3 = S3Plugin.amazonS3;
		if (amazonS3 == null) {
            Logger.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            amazonS3.deleteObject(bucket, getActualFileName());
            super.delete();
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("S3File [id=").append(id).append(", name=").append(name)
				.append(", file=").append(file).append(", URL=").append(getUrl()).append("]");
		return builder.toString();
	}
}
