package plugins;

import java.util.Set;

import play.Application;
import play.Configuration;
import play.Logger;
import play.Logger.ALogger;
import play.api.Plugin;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Region;

public class S3Plugin implements Plugin {

	private static ALogger log = Logger.of(S3Plugin.class);

	public static final String AWS_S3_BUCKET = "aws.s3.bucket";
	public static final String AWS_ACCESS_KEY = "aws.access.key";
	public static final String AWS_SECRET_KEY = "aws.secret.key";
	private final Application application;

	private static String bucket;

	public static AmazonS3 amazonS3;

	public S3Plugin(Application application) {
		this.application = application;
	}

	@Override
	public void onStart() {
		if (log.isDebugEnabled())
			log.debug("onStart <-");
		Configuration conf = application.configuration();
		String accessKey = conf.getString(AWS_ACCESS_KEY);
		String secretKey = conf.getString(AWS_SECRET_KEY);
		String s3Bucket = conf.getString(AWS_S3_BUCKET);
		if (log.isDebugEnabled())
			log.debug("accessKey : " + accessKey);
		if (log.isDebugEnabled())
			log.debug("s3Bucket : " + s3Bucket);
		bucket = s3Bucket;

		if ((accessKey != null) && (secretKey != null)) {
			AWSCredentials credentials = new BasicAWSCredentials(accessKey,
					secretKey);
			ClientConfiguration config = new ClientConfiguration();
			String proxyHost = System.getProperty("http.proxyHost");
			String proxyPort = System.getProperty("http.proxyPort");
			if (proxyHost != null && proxyPort != null) {
				config.setProxyHost(proxyHost);
				config.setProxyPort(Integer.valueOf(proxyPort));
			}

			if (amazonS3 == null)
				amazonS3 = new AmazonS3Client(credentials, config);
			
			if (amazonS3.doesBucketExist(s3Bucket)) {
				if (log.isDebugEnabled())
					log.debug("bucket exists: " + s3Bucket);
			} else {
				log.debug("bucket does not exist: " + s3Bucket);
				amazonS3.createBucket(s3Bucket, Region.EU_Ireland);
				log.debug("bucket created: " + s3Bucket);
			}

			log.info("Using S3 Bucket: " + s3Bucket);
		}
	}

	@Override
	public boolean enabled() {
		if (log.isDebugEnabled())
			log.debug("enabled() <-");
		
		Set<String> keys = application.configuration().keys();
		boolean result = keys.contains(AWS_ACCESS_KEY) && keys.contains(AWS_SECRET_KEY) && keys
				.contains(AWS_S3_BUCKET);
		
		if (log.isDebugEnabled())
			log.debug("enabled : " + result);
		return result;
	}

	@Override
	public void onStop() {
		amazonS3 = null;
	}

	public static String getBucket() {
		return bucket;
	}

}