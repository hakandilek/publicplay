package admin;

import java.util.ArrayList;
import java.util.List;

import models.CRUDModel;
import play.api.templates.Html;
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

	public Html renderListPage(Page<CRUDModel> page, String entityName,
			String keyFieldName, List<String> fieldNames) {
		return userList.render(page, entityName, keyFieldName, listFieldNames);
	}

}
