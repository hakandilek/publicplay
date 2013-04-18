package controllers.crud;

import static models.ContentStatus.APPROVED;
import static models.ContentStatus.NEW;
import static models.ContentStatus.REMOVED;
import static models.ContentStatus.UPDATED;
import static play.libs.Json.toJson;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;

import models.Comment;
import models.ContentReport;
import models.ContentStatus;
import models.User;
import models.ContentReport.ContentType;
import models.dao.CommentDAO;
import models.dao.ContentReportDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class CommentAPIController extends APIController<Long, Comment> {


	protected CommentDAO commentDAO;
	private ContentReportDAO contentReportDAO;

	@Inject
	public CommentAPIController(CommentDAO commentDAO, ContentReportDAO contentReportDAO) {
		super(commentDAO);
		this.commentDAO = commentDAO;
		this.contentReportDAO = contentReportDAO;
	}

	@Override
	public Result create() {
		/* TODO:
		Result check = checkRequired("url");
		if (check != null) {
			return check;
		}

		String url = jsonText("url");
		try {
			new URL(url );
		} catch (MalformedURLException e) {
			return badRequest(toJson(ImmutableMap.of(
					"status", "error", 
					"message", e.getMessage())));
		}
		
		Comment m = new Comment();
		m.setStatus(Comment.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}

	public Result approve(Long key) {
		if (log.isDebugEnabled())
			log.debug("approve <- " + key);
		
		Comment comment = commentDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("comment : " + comment);
		if (comment == null)
			return notFound();

		ContentStatus status = comment.getStatus();
		if (status == NEW || status == UPDATED) {
			User user = HttpUtils.loginUser(ctx());
			comment.setStatus(APPROVED);
			comment.setApprovedBy(user);
			comment.setApprovedOn(new Date());
			commentDAO.update( comment);

			List<ContentReport> reports = contentReportDAO.findForContent(ContentType.POST, key);
			for (ContentReport report : reports) {
				if (report.getStatus() == ContentReport.Status.NEW) {
					report.setStatus(ContentReport.Status.IGNORED);
					report.setUpdatedBy(user);
					contentReportDAO.update(report);
				}
			}
			return ok(toJson(ImmutableMap.of("status", "ok", "key", key)));
		} else {
			return badRequest(toJson(ImmutableMap.of("status", "error",
					"message", "wrong status", "status", status.name())));
		}
	}

	public Result remove(Long key) {
		if (log.isDebugEnabled())
			log.debug("remove <- " + key);

		Comment comment = commentDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("comment : " + comment);
		if (comment == null)
			return notFound();

		User user = HttpUtils.loginUser(ctx());
		comment.setStatus(REMOVED);
		comment.setUpdatedBy(user);
		commentDAO.update(comment);
		
		List<ContentReport> reports = contentReportDAO.findForContent(ContentType.POST, key);
		for (ContentReport report : reports) {
			if (report.getStatus() == ContentReport.Status.NEW) {
				report.setStatus(ContentReport.Status.PROCESSED);
				report.setUpdatedBy(user);
				contentReportDAO.update(report);
			}
		}
		return ok(toJson(ImmutableMap.of("status", "ok", "key", key)));
	}


}
