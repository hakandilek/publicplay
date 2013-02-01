package models.dao;

import java.util.UUID;

import models.S3File;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampDAO;

public class S3FileDAO extends TimestampDAO<UUID, S3File> {

	public S3FileDAO() {
		super(new CachedDAO<UUID, S3File>(UUID.class, S3File.class));
	}

}
