package controllers.crud;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import models.Reputation;
import models.ReputationValue;
import models.User;
import models.dao.ReputationValueDAO;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Call;
import play.mvc.Result;
import play.mvc.Results;
import static play.mvc.Results.async;
import play.utils.crud.CRUDController;

import com.avaje.ebean.Page;

import controllers.routes;
import forms.BulkUser;

public class UserCRUDController extends CRUDController<String, User> {

	private static final int PAGE_SIZE = 20;

	private static ALogger log = Logger.of(UserCRUDController.class);

	private UserDAO userDAO;

	private Form<BulkUser> bulkForm = form(BulkUser.class);

	private ReputationValueDAO reputationValueDAO;

	@Inject
	public UserCRUDController(UserDAO userDAO,
			ReputationValueDAO reputationValueDAO) {
		super(userDAO, form(User.class), String.class, User.class, PAGE_SIZE,
				"lastLogin desc");
		this.userDAO = userDAO;
		this.reputationValueDAO = reputationValueDAO;
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

	public Result approve(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userApprove() <-");

		User user = userDAO.get(key);
		user.setStatus(User.Status.APPROVED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		userDAO.update(key, user);
		return list(null, page);
	}

	public Result suspend(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userSuspend() <-");

		User user = userDAO.get(key);
		user.setStatus(User.Status.SUSPENDED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		userDAO.update(key, user);
		return list(null, page);
	}

	public Result recalculateReputation(final String key,
			final int page) {
		User user = userDAO.get(key);
		updateReputation(user);
		return list(null, page);
	}

	private void updateReputation(User user) {
		

		for (Reputation reputation : user.getReputations()) {
			ReputationValue reputationValueInTable = reputationValueDAO
					.get(reputation.getName());
			int valueInTable = reputationValueInTable.getValue();
			if (reputation.getValue() != valueInTable) {
				reputation.setValue(valueInTable);
			}
		}
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		userDAO.update(user.getKey(), user);
	}

	public Result list(String status, int page) {
		if (log.isDebugEnabled())
			log.debug("list() <-");
		if (log.isDebugEnabled())
			log.debug("status : " + status);

		Page<User> p = null;
		if (status == null || "".equals(status)) {
			p = userDAO.find().where().orderBy("lastLogin desc")
					.findPagingList(PAGE_SIZE).getPage(page);
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

		return ok(templateForBulkForm(), with(Form.class, bulkForm));
	}

	public Result createBulk() {
		if (log.isDebugEnabled())
			log.debug("createBulk() <-");

		Form<BulkUser> filledForm = bulkForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForBulkForm(), with(Form.class, bulkForm));
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

	public Result calculateAllReputations() {
		final List<User> users = userDAO.find().where().orderBy("lastLogin desc")
				.findList();

		return async(Akka.future(new Callable<Void>() {
			public Void call() throws Exception {
				for(User user :users){
					updateReputation(user);
				}
				return null;
			}
		}).map(new F.Function<Void, Result>() {
			public Result apply(Void arg0) {
				return redirect(toIndex());
			}
		}));
	}

}
