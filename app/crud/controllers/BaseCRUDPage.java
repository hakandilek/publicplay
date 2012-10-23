package crud.controllers;

import java.util.List;

import models.CRUDForm;
import models.CRUDModel;
import play.api.templates.Html;

import com.avaje.ebean.Page;

import crud.controllers.CRUDPage;
import views.html.crud.editForm;
import views.html.crud.list;
import views.html.crud.show;

public class BaseCRUDPage<K> implements CRUDPage<K> {

	public Html renderEditPage(String entityName, K key, String keyFieldName, CRUDForm form) {
		return editForm.render(entityName, key, keyFieldName, form);
	}

	public Html renderListPage(Page<CRUDModel> page, String entityName,
			String keyFieldName, List<String> fieldNames) {
		return list.render(page, entityName, keyFieldName, fieldNames);
	}

	public Html renderShowPage(String entityName, CRUDModel model) {
		return show.render(entityName, model);
	}

}
