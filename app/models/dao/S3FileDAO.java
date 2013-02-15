package models.dao;

import java.util.UUID;

import javax.inject.Singleton;

import models.S3File;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

@Singleton
public class S3FileDAO extends CachedDAO<UUID, S3File> {

	public S3FileDAO() {
		super(UUID.class, S3File.class);
		addListener(new TimestampListener<UUID, S3File>());
	}

}
