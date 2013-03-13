package controllers.crud;

import static play.libs.Json.toJson;
import static models.ContentStatus.*; 

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;

import models.ContentReport;
import models.ContentReport.ContentType;
import models.ContentStatus;
import models.Post;
import models.User;
import models.dao.ContentReportDAO;
import models.dao.PostDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.APIController;


public class PostAPIController extends APIController<Long, Post> {

	private static ALogger log = Logger.of(PostAPIController.class);
	private ContentReportDAO contentReportDAO;
	private PostDAO postDAO;

	@Inject
	public PostAPIController(PostDAO postDAO, ContentReportDAO contentReportDAO) {
		super(postDAO);
		this.postDAO = postDAO;
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
		
		Post m = new Post();
		m.setStatus(Post.Status.NEW);
		m.setUrl(url);
		Long key = postDAO.create(m);
		*/
		return TODO;
	}

	public Result approve(Long key) {
		if (log.isDebugEnabled())
			log.debug("approve <- " + key);
		
		Post post = postDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		if (post == null)
			return notFound();

		ContentStatus status = post.getStatus();
		if (status == NEW || status == UPDATED) {
			User user = HttpUtils.loginUser(ctx());
			post.setStatus(APPROVED);
			post.setApprovedBy(user);
			post.setApprovedOn(new Date());
			postDAO.update(key, post);

			List<ContentReport> reports = contentReportDAO.findForContent(ContentType.POST, key);
			for (ContentReport report : reports) {
				if (report.getStatus() == ContentReport.Status.NEW) {
					report.setStatus(ContentReport.Status.IGNORED);
					report.setUpdatedBy(user);
					contentReportDAO.update(key, report);
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

		Post post = postDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		if (post == null)
			return notFound();

		User user = HttpUtils.loginUser(ctx());
		post.setStatus(REMOVED);
		post.setUpdatedBy(user);
		postDAO.update(key, post);
		
		List<ContentReport> reports = contentReportDAO.findForContent(ContentType.POST, key);
		for (ContentReport report : reports) {
			if (report.getStatus() == ContentReport.Status.NEW) {
				report.setStatus(ContentReport.Status.PROCESSED);
				report.setUpdatedBy(user);
				contentReportDAO.update(key, report);
			}
		}
		return ok(toJson(ImmutableMap.of("status", "ok", "key", key)));
	}

	public Result expire(Long key) {
		if (log.isDebugEnabled())
			log.debug("expire <- " + key);
		
		Post post = postDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("post : " + post);
		if (post == null)
			return notFound();

		ContentStatus status = post.getStatus();
		if (status == NEW || status == UPDATED || status == APPROVED) {
			User user = HttpUtils.loginUser(ctx());
			post.setStatus(EXPIRED);
			post.setUpdatedBy(user);
			postDAO.update(key, post);

			List<ContentReport> reports = contentReportDAO.findForContent(ContentType.POST, key);
			for (ContentReport report : reports) {
				if (report.getStatus() == ContentReport.Status.NEW) {
					report.setStatus(ContentReport.Status.PROCESSED);
					report.setUpdatedBy(user);
					contentReportDAO.update(key, report);
				}
			}
			return ok(toJson(ImmutableMap.of("status", "ok", "key", key)));
		} else {
			return badRequest(toJson(ImmutableMap.of("status", "error",
					"message", "wrong status", "status", status.name())));
		}
	}


}
