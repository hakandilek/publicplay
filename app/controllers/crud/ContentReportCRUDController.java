package controllers.crud;

import javax.inject.Inject;

import models.ContentReport;
import models.dao.ContentReportDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class ContentReportCRUDController extends CRUDController<Long, ContentReport>  {

	@Inject
	public ContentReportCRUDController(ContentReportDAO dao) {
		super(dao, form(ContentReport.class), Long.class, ContentReport.class, 20, "updatedOn desc");
	}

	@Override
	protected String templateForForm() {
		return "admin.contentReportForm";
	}

	@Override
	protected String templateForList() {
		return "admin.contentReportList";
	}

	@Override
	protected String templateForShow() {
		return "admin.contentReportShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.contentReportList(0);
	}

}
