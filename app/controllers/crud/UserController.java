package controllers.crud;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.User;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import socialauth.core.Secure;
import utils.HttpUtils;
import views.html.userShow;
import controllers.routes;

public class UserController extends CRUDController<String, User> {

	private static ALogger log = Logger.of(UserController.class);

	@Inject
	public UserController(UserDAO dao) {
		super(dao, form(User.class), String.class, User.class);
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
		return routes.Admin.userList();
	}

	public Result show(String key) {
		if (log.isDebugEnabled())
			log.debug("show() <-");
		if (log.isDebugEnabled())
			log.debug("key : " + key);

		User userToBeShowed = null;
		if (null != key) {
			userToBeShowed = User.get(key);
		}
		final Set<Long> upVotes = userToBeShowed == null ? new TreeSet<Long>() : userToBeShowed.getUpVotedPostKeys();
		final Set<Long> downVotes = userToBeShowed == null ? new TreeSet<Long>() : userToBeShowed.getDownVotedPostKeys();

		User loggedInUser = HttpUtils.loginUser(ctx());
		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		if (loggedInUser == null || userToBeShowed == null) {
			return badRequest(userShow.render(loggedInUser, userToBeShowed,
					false,upVotes, downVotes));
		}
		boolean selfPage = false;
		if (loggedInUser != null && userToBeShowed != null
				&& (loggedInUser.getKey() + "").equals(userToBeShowed.getKey())) {
			selfPage = true;
		}

		return ok(userShow.render(loggedInUser, userToBeShowed, selfPage,upVotes, downVotes));
	}

	@Secure
	public Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");

		User loggedInUser = HttpUtils.loginUser(ctx());
		final Set<Long> upVotes = loggedInUser == null ? new TreeSet<Long>() : loggedInUser.getUpVotedPostKeys();
		final Set<Long> downVotes = loggedInUser == null ? new TreeSet<Long>() : loggedInUser.getDownVotedPostKeys();

		if (log.isDebugEnabled())
			log.debug("user : " + loggedInUser);
		boolean selfPage = true;

		return ok(userShow.render(loggedInUser, loggedInUser, selfPage,upVotes, downVotes));
	}

	public Result approve(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userApprove() <-");
		
		User user = User.get(key);
		user.setStatus(User.Status.APPROVED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		User.update(user);
		return listAll();
	}

	public Result suspend(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userSuspend() <-");
		
		User user = User.get(key);
		user.setStatus(User.Status.SUSPENDED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		User.update(user);
		return listAll();
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
