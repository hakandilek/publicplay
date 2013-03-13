package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.UserFollow;
import models.UserFollowPK;
import models.dao.UserFollowDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class UserFollowCRUDController extends CRUDController<UserFollowPK, UserFollow> {

	@Inject
	public UserFollowCRUDController(UserFollowDAO dao) {
		super(dao, form(UserFollow.class), UserFollowPK.class, UserFollow.class, 20, "updatedOn desc");
	}

	@Override
	protected String templateForForm() {
		return "admin.userFollowForm";
	}

	@Override
	protected String templateForList() {
		return "admin.userFollowList";
	}

	@Override
	protected String templateForShow() {
		return "admin.userFollowShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.index();
	}

}
