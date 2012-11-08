package crud.controllers;

import java.util.List;

import models.CRUDForm;
import models.CRUDModel;
import models.User;
import play.api.templates.Html;
import play.mvc.Http.Context;

import com.avaje.ebean.Page;

import crud.controllers.CRUDPage;
import utils.HttpUtils;
import views.html.crud.editForm;
import views.html.crud.list;
import views.html.crud.show;

public class BaseCRUDPage<K> implements CRUDPage<K> {

	public Html renderEditPage(Context context, String entityName, K key,
			String keyFieldName, CRUDForm form) {
		User user = HttpUtils.loginUser(context);
		return editForm.render(entityName, key, keyFieldName, form, user);
	}

	public Html renderListPage(Context context, String entityName,
			Page<CRUDModel> page, String keyFieldName, List<String> fieldNames) {
		User user = HttpUtils.loginUser(context);
		return list.render(page, entityName, keyFieldName, fieldNames, user);
	}

	public Html renderShowPage(Context context, String entityName,
			CRUDModel model) {
		User user = HttpUtils.loginUser(context);
		return show.render(entityName, model, user);
	}

}
