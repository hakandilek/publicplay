package models.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.ContentReport;
import models.ContentReport.ContentType;
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

	public List<ContentReport> findForContent(ContentType type, Long contentKey) {
		return find.where().eq("contentType", type)
				.eq("contentKey", contentKey).orderBy("createdBy desc")
				.findList();
	}

}
