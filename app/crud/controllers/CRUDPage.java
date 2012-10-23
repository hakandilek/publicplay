package crud.controllers;

import java.util.List;

import com.avaje.ebean.Page;

import models.CRUDForm;
import models.CRUDModel;
import play.api.templates.Html;

public interface CRUDPage<K> {

	Html renderEditPage(String entityName, K key, String keyFieldName, CRUDForm form);
	
	Html renderListPage(Page<CRUDModel> page, String entityName, String keyFieldName, List<String> fieldNames);

	Html renderShowPage(String entityName, CRUDModel model);

}
