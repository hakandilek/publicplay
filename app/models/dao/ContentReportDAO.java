package models.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.ContentReport;
import play.utils.dao.CachedDAO;
import play.utils.dao.TimestampListener;

@Singleton
public class ContentReportDAO extends CachedDAO<Long, ContentReport> {

	@Inject
	public ContentReportDAO(OwnerCacheCleaner<Long, ContentReport> cacheCleaner) {
		super(Long.class, ContentReport.class);
		addListener(new TimestampListener<Long, ContentReport>());
		addListener(cacheCleaner);
	}

}
