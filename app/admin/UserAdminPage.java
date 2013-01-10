package admin;

import java.util.ArrayList;
import java.util.List;

import models.CRUDModel;
import models.User;
import play.api.templates.Html;
import play.mvc.Http.Context;
import utils.HttpUtils;
import views.html.admin.userList;

import com.avaje.ebean.Page;

import crud.controllers.BaseCRUDPage;

public class UserAdminPage extends BaseCRUDPage<String>{

	private final List<String> listFieldNames = new ArrayList<String>();

	public UserAdminPage() {
		super();
		listFieldNames.add("User.key");
		listFieldNames.add("User.profile");
		listFieldNames.add("User.lastLogin");
		listFieldNames.add("User.loginCount");
	}
	
	@Override
	public Html renderListPage(Context context, String entityName,
			Page<CRUDModel> page, String keyFieldName, List<String> fieldNames) {
		User user = HttpUtils.loginUser(context);
		return userList.render(page, entityName, keyFieldName, listFieldNames, user, null);
	}

}
