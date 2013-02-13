package controllers.crud;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.User;
import models.dao.PostRatingDAO;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import views.html.userShow;

import com.avaje.ebean.Page;

import controllers.HttpUtils;
import controllers.routes;
import forms.BulkUser;

public class UserController extends CRUDController<String, User> {

	private static final int PAGE_SIZE = 20;
	private static ALogger log = Logger.of(UserController.class);
	private PostRatingDAO postRatingDAO;
	private UserDAO userDAO;
	private HttpUtils httpUtils;
	private Form<BulkUser> bulkForm = form(BulkUser.class);

	@Inject
	public UserController(UserDAO userDAO, PostRatingDAO postRatingDAO, HttpUtils httpUtils) {
		super(userDAO, form(User.class), String.class, User.class, PAGE_SIZE, "lastLogin desc");
		this.userDAO = userDAO;
		this.postRatingDAO = postRatingDAO;
		this.httpUtils = httpUtils;
	}

	@Override
	protected String templateForForm() {
		return "admin.userForm";
	}

	@Override
	protected String templateForList() {
		return "admin.userList";
	}

	@Override
	protected String templateForShow() {
		return "admin.userShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.userList(null, 0);
	}

	public Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("show() <- " + key);
		User loginUser = httpUtils.loginUser();
		User userToShow = null;
		if (null != key)
			userToShow = userDAO.get(key);
		return show(loginUser, userToShow);
	}

	public Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");
		User loginUser = httpUtils.loginUser(ctx());
		return show(loginUser, loginUser);
	}

	private Result show(User loginUser, User userToShow) {

		Set<Long> upVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(userToShow);
		Set<Long> downVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(userToShow);

		if (log.isDebugEnabled())
			log.debug("user : " + loginUser);
		if (loginUser == null || userToShow == null) {
			return badRequest(userShow.render(userToShow, false,
					upVotes, downVotes));
		}

		boolean selfPage = false;
		if (loginUser != null && userToShow != null
				&& (loginUser.getKey() + "").equals(userToShow.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(userToShow, selfPage, upVotes,
				downVotes));
	}

	public Result approve(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userApprove() <-");
		
		User user = userDAO.get(key);
		user.setStatus(User.Status.APPROVED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		userDAO.update(key, user);
		return list(page);
	}

	public Result suspend(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userSuspend() <-");
		
		User user = userDAO.get(key);
		user.setStatus(User.Status.SUSPENDED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		userDAO.update(key, user);
		return list(page);
	}

	public Result list(String status, int page) {
		if (log.isDebugEnabled())
			log.debug("list() <-");
		if (log.isDebugEnabled())
			log.debug("status : " + status);
		
		Page<User> p = null;
		if (status == null || "".equals(status)) {
			p = userDAO.find().where()
					.orderBy("lastLogin desc").findPagingList(PAGE_SIZE)
					.getPage(page);
		} else {
			User.Status s = User.Status.valueOf(status);
			p = userDAO.find().where().eq("status", s)
					.orderBy("lastLogin desc").findPagingList(PAGE_SIZE)
					.getPage(page);
		}
		
		return ok(templateForList(),
				with(Page.class, p).and(String.class, status));
	}

	protected String templateForBulkForm() {
		return "admin.userBulkForm";
	}

	public Result newBulkForm() {
		if (log.isDebugEnabled())
			log.debug("newBulkForm() <-");

		return ok(templateForBulkForm(),
				with(Form.class, bulkForm));
	}

	public Result createBulk() {
		if (log.isDebugEnabled())
			log.debug("createBulk() <-");

		Form<BulkUser> filledForm = bulkForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForBulkForm(),
					with(Form.class, bulkForm));
		} else {
			BulkUser formModel = filledForm.get();
			List<User> models = formModel.toModel();
			if (log.isDebugEnabled())
				log.debug("models : " + models);
			for (User user : models) {
				if (log.isDebugEnabled())
					log.debug("user : " + user);
				
				User dbUser = userDAO.get(user.getKey());
				if (dbUser == null) {
					userDAO.create(user);
				}
			}
			if (log.isDebugEnabled())
				log.debug("entity created");

			return redirect(toIndex());
		}
	}

}
