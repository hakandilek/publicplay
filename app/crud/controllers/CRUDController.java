package crud.controllers;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.crud.*;

public class CRUDController extends Controller {

	private static ALogger log = Logger.of(CRUD.class);

	private final Map<String, CRUD<? extends Serializable, ?>> models = new TreeMap<String, CRUD<?, ?>>();

	public CRUDController(CRUD<?, ?>[] cruds) {
		for (CRUD<?, ?> crud : cruds) {
			String name = crud.getName();
			log.info("registering CRUD controller: " + name);
			models.put(name, crud);
		}
	}

	protected Result doIndex() {
		return ok(index.render(models.keySet()));
	}

	protected Result doList(String model, int page) {
		CRUD<?, ?> crud = models.get(model);
		if (crud != null) {
			return crud.list(page);
		}
		return notFound(model);
	}

	protected Result doNewForm(String model) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Result doCreate(String model) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Result doEditForm(String model, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Result doUpdate(String model, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Result doDelete(String model, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Result doShow(String model, String key) {
		CRUD<? extends Serializable, ?> c = models.get(model);
		try {
			Long intKey = Long.getLong(key);
			return ((CRUD<Long, ?>) c).get(intKey);
		} catch (Exception e) {
			return ((CRUD<String, ?>) c).get(key);
		}
	}

}
