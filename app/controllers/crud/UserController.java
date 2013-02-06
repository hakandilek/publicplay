package controllers.crud;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.User;
import models.dao.PostRatingDAO;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import views.html.userShow;
import controllers.HttpUtils;
import controllers.routes;

public class UserController extends CRUDController<String, User> {

	private static ALogger log = Logger.of(UserController.class);
	private PostRatingDAO postRatingDAO;
	private UserDAO userDAO;
	private HttpUtils httpUtils;

	@Inject
	public UserController(UserDAO userDAO, PostRatingDAO postRatingDAO, HttpUtils httpUtils) {
		super(userDAO, form(User.class), String.class, User.class, 20, "lastLogin desc");
		this.userDAO = userDAO;
		this.postRatingDAO = postRatingDAO;
		this.httpUtils = httpUtils;
	}

	@Override
	protected String templateForForm() {
		return "userForm";
	}

	@Override
	protected String templateForList() {
		return "userList";
	}

	@Override
	protected String templateForShow() {
		return "userShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.userList(0);
	}

	public Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("show() <- " + key);
		User loginUser = httpUtils.loginUser(ctx());
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
			return badRequest(userShow.render(loginUser, userToShow, false,
					upVotes, downVotes));
		}

		boolean selfPage = false;
		if (loginUser != null && userToShow != null
				&& (loginUser.getKey() + "").equals(userToShow.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(loginUser, userToShow, selfPage, upVotes,
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
		//TODO:login user 
		//User user = HttpUtils.loginUser(ctx());
		models.User.Status userStatus = models.User.Status.valueOf(status);
		if (userStatus == null) userStatus = models.User.Status.NEW;
		if (log.isDebugEnabled())
			log.debug("userStatus : " + userStatus);

		//TODO: status page
		return TODO;
	}

}
