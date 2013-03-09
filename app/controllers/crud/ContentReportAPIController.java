package controllers.crud;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import models.ContentReport;
import models.ContentReport.ContentType;
import models.ContentReport.Reason;
import models.User;
import models.dao.ContentReportDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.APIController;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;

public class ContentReportAPIController extends
		APIController<Long, ContentReport> {

	private static ALogger log = Logger.of(ContentReportAPIController.class);
	
	@Inject
	public ContentReportAPIController(ContentReportDAO ContentReportDAO) {
		super(ContentReportDAO);
	}

	@Override
	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create <-");
		
		Result check = checkRequired("contentKey", "contentType", "reason", "comment");
		System.out.println(check);
		if (check != null) {
			if (log.isDebugEnabled())
				log.debug("check : " + check);

			return check;
		}

		String contentKeyString = jsonText("contentKey");
		String contentTypeString = jsonText("contentType");
		String reasonString = jsonText("reason");
		String comment = jsonText("comment");
		Long contentKey = Long.valueOf(contentKeyString);
		ContentType contentType = ContentType.valueOf(contentTypeString);
		Reason reason = Reason.valueOf(reasonString);
		User user = HttpUtils.loginUser();

		ContentReport m = new ContentReport();
		m.setStatus(ContentReport.Status.NEW);
		m.setComment(comment);
		m.setContentKey(contentKey);
		m.setContentType(contentType);
		m.setReason(reason);
		m.setCreatedBy(user);
		
		Long key = dao.create(m);
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		return created(toJson(ImmutableMap.of("status", "OK", "key", key,
				"data", m)));
	}

}
