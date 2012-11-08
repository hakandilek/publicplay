package crud.controllers;

import java.util.List;

import com.avaje.ebean.Page;

import models.CRUDForm;
import models.CRUDModel;
import play.api.templates.Html;
import play.mvc.Http.Context;

public interface CRUDPage<K> {

	Html renderEditPage(Context context, String entityName, K key, String keyFieldName, CRUDForm form);
	
	Html renderListPage(Context context, String entityName, Page<CRUDModel> page, String keyFieldName, List<String> fieldNames);

	Html renderShowPage(Context context, String entityName, CRUDModel model);

}
