package controllers.crud;

import java.util.List;

import javax.inject.Inject;

import com.avaje.ebean.Page;

import models.Comment;
import models.ContentReport;
import models.ContentReport.ContentType;
import models.Post;
import models.dao.CommentDAO;
import models.dao.ContentReportDAO;
import models.dao.PostDAO;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import controllers.routes;

public class ContentReportCRUDController extends CRUDController<Long, ContentReport>  {

	private static final String ORDER_BY = "contentKey desc, contentType";

	private static final int PAGE_SIZE = 20;
	
	private ContentReportDAO contentReportDAO;

	private PostDAO postDAO;

	private CommentDAO commentDAO;

	@Inject
	public ContentReportCRUDController(ContentReportDAO dao, PostDAO postDAO, CommentDAO commentDAO) {
		super(dao, form(ContentReport.class), Long.class, ContentReport.class, PAGE_SIZE, ORDER_BY);
		contentReportDAO = dao;
		this.postDAO = postDAO;
		this.commentDAO = commentDAO;
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
			//TODO:move into DAO
			p = contentReportDAO.find().where().orderBy(ORDER_BY)
					.findPagingList(PAGE_SIZE).getPage(page);
		} else {
			ContentReport.Status s = ContentReport.Status.valueOf(status);
			//TODO:move into DAO
			p = contentReportDAO.find().where().eq("status", s)
					.orderBy(ORDER_BY).findPagingList(PAGE_SIZE)
					.getPage(page);
		}

		return ok(templateForList(),
				with(Page.class, p).and(String.class, status));
	}

	public Result show(String contentType, Long contentKey, Long key) {
		if (log.isDebugEnabled())
			log.debug("show <-");
		if (log.isDebugEnabled())
			log.debug("contentKey : " + contentKey);
		if (log.isDebugEnabled())
			log.debug("key : " + key);
		
		ContentReport report = contentReportDAO.get(key);
		ContentType type = ContentType.valueOf(contentType);
		List<ContentReport> reports = contentReportDAO.findForContent(type, contentKey);
		switch (type) {
		case COMMENT:
			Comment comment = commentDAO.get(contentKey);
			Post post = comment.getPost();
			return ok("admin.contentReportShowComment",
					with(ContentReport.class, report).and(List.class, reports).and(Post.class, post).and(Comment.class, comment));
		case POST:
		default:
			post = postDAO.get(contentKey);
			return ok("admin.contentReportShowPost",
					with(ContentReport.class, report).and(List.class, reports).and(Post.class, post));
		}

	}

}
