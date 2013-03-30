package models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.Logger;
import play.Logger.ALogger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.utils.dao.TimestampModel;
import plugins.S3Plugin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Entity
@Table(name="TBL_S3FILE")
@SuppressWarnings("serial")
public class S3File extends Model implements TimestampModel<UUID> {

	private static ALogger log = Logger.of(S3File.class);
	
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
    private S3Input input;

	public String getBucket() {
		return bucket;
	}

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
	
    public void setInput(S3Input input) {
		this.input = input;
	}
    
    public void setInputFromFile(File file) {
    	this.input = new S3InputFile(file);
    }
    
    public void setInputFromData(byte[] data) {
    	this.input = new S3InputData(data);
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
            log.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.getBucket();
            
            super.save(); // assigns an id

            if (log.isDebugEnabled())
				log.debug("bucket : " + bucket);
            if (log.isDebugEnabled())
				log.debug("file : " + input);

            String filename = getActualFileName();
            if (log.isDebugEnabled())
				log.debug("filename : " + filename);

            
			PutObjectRequest putObjectRequest = input.getPutObjectRequest();
				
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    @Override
    public void delete() {
        AmazonS3 amazonS3 = S3Plugin.amazonS3;
		if (amazonS3 == null) {
            log.error("Could not delete because amazonS3 was null");
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
				.append(", input=").append(input).append(", URL=").append(getUrl()).append("]");
		return builder.toString();
	}

	interface S3Input {
		PutObjectRequest getPutObjectRequest();
	}

	class S3InputFile implements S3Input {
		private File file;
		public S3InputFile(File file) {
			this.file = file;
		}
		public PutObjectRequest getPutObjectRequest() {
			return new PutObjectRequest(bucket, getActualFileName(), file);
		}
	}

	class S3InputData implements S3Input {
		private byte[] data;
		public S3InputData(byte[] data) {
			this.data = data;
		}
		public PutObjectRequest getPutObjectRequest() {
			Long contentLength = Long.valueOf(data.length);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(contentLength);
			InputStream is = new ByteArrayInputStream(data);
			String fn = getActualFileName();
			return new PutObjectRequest(bucket, fn, is, metadata);
		}
	}
}
