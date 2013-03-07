package controllers.crud;

import static play.libs.Json.toJson;

import javax.inject.Inject;

import models.ContentReport;
import models.ContentReport.ContentType;
import models.ContentReport.Reason;
import models.User;
import models.dao.ContentReportDAO;
import play.mvc.Result;
import play.utils.crud.APIController;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;

public class ContentReportAPIController extends
		APIController<Long, ContentReport> {

	@Inject
	public ContentReportAPIController(ContentReportDAO ContentReportDAO) {
		super(ContentReportDAO);
	}

	@Override
	public Result create() {
		Result check = checkRequired("contentKey", "contentType", "reason");
		if (check != null) {
			return check;
		}

		String contentKeyString = jsonText("contentKey");
		String contentTypeString = jsonText("contentType");
		String reasonString = jsonText("contentReason");
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
		return created(toJson(ImmutableMap.of("status", "OK", "key", key,
				"data", m)));
	}

}
