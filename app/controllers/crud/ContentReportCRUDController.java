package controllers.crud;

import javax.inject.Inject;

import com.avaje.ebean.Page;

import models.ContentReport;
import models.dao.ContentReportDAO;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import controllers.routes;

public class ContentReportCRUDController extends CRUDController<Long, ContentReport>  {

	private static final int PAGE_SIZE = 20;
	
	private ContentReportDAO contentReportDAO;

	@Inject
	public ContentReportCRUDController(ContentReportDAO dao) {
		super(dao, form(ContentReport.class), Long.class, ContentReport.class, PAGE_SIZE, "updatedOn desc");
		contentReportDAO = dao;
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
		return routes.Admin.contentReportList(null, 0);
	}

	public Result list(String status, int page) {
		if (log.isDebugEnabled())
			log.debug("list() <-");
		if (log.isDebugEnabled())
			log.debug("status : " + status);

		Page<ContentReport> p = null;
		if (status == null || "".equals(status)) {
			p = contentReportDAO.find().where().orderBy("createdOn desc")
					.findPagingList(PAGE_SIZE).getPage(page);
		} else {
			ContentReport.Status s = ContentReport.Status.valueOf(status);
			p = contentReportDAO.find().where().eq("status", s)
					.orderBy("createdOn desc").findPagingList(PAGE_SIZE)
					.getPage(page);
		}

		return ok(templateForList(),
				with(Page.class, p).and(String.class, status));
	}


}
