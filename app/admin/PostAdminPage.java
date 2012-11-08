package admin;

import java.util.ArrayList;
import java.util.List;

import models.CRUDForm;
import models.CRUDModel;
import models.User;
import play.api.templates.Html;
import play.mvc.Http.Context;
import utils.HttpUtils;
import views.html.admin.postList;

import com.avaje.ebean.Page;

import crud.controllers.BaseCRUDPage;

public class PostAdminPage extends BaseCRUDPage<Long> {

	private final List<String> listFieldNames = new ArrayList<String>();

	public PostAdminPage() {
		super();
		listFieldNames.add("Post.key");
		listFieldNames.add("Posts");
		listFieldNames.add("Post.created");
		listFieldNames.add("Post.updated");
	}

	@Override
	public Html renderEditPage(Context context, String entityName, Long key,
			String keyFieldName, CRUDForm form) {
		// TODO Auto-generated method stub
		return super.renderEditPage(context, entityName, key, keyFieldName, form);
	}

	@Override
	public Html renderShowPage(Context context, String entityName,
			CRUDModel model) {
		// TODO Auto-generated method stub
		return super.renderShowPage(context, entityName, model);
	}

	@Override
	public Html renderListPage(Context context, String entityName,
			Page<CRUDModel> page, String keyFieldName, List<String> fieldNames) {
		User user = HttpUtils.loginUser(context);
		return postList.render(page, entityName, keyFieldName, listFieldNames, user);
	}

	
}
