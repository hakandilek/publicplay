package crud.controllers;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.crud.index;

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
		CRUD<?, ?> crud = models.get(model);
		if (crud != null) {
			return crud.newForm();
		}
		return notFound(model);
	}

	protected Result doCreate(String model) {
		CRUD<?, ?> crud = models.get(model);
		if (crud != null) {
			return crud.create();
		}
		return notFound(model);
	}

	@SuppressWarnings("unchecked")
	protected Result doEditForm(String model, String key) {
		CRUD<? extends Serializable, ?> c = models.get(model);
		try {
			Long intKey = Long.getLong(key);
			return ((CRUD<Long, ?>) c).editForm(intKey);
		} catch (Exception e) {
			return ((CRUD<String, ?>) c).editForm(key);
		}
	}

	@SuppressWarnings("unchecked")
	protected Result doUpdate(String model, String key) {
		CRUD<? extends Serializable, ?> c = models.get(model);
		try {
			Long intKey = Long.getLong(key);
			return ((CRUD<Long, ?>) c).update(intKey);
		} catch (Exception e) {
			return ((CRUD<String, ?>) c).update(key);
		}
	}

	@SuppressWarnings("unchecked")
	protected Result doDelete(String model, String key) {
		CRUD<? extends Serializable, ?> c = models.get(model);
		try {
			Long intKey = Long.getLong(key);
			return ((CRUD<Long, ?>) c).delete(intKey);
		} catch (Exception e) {
			return ((CRUD<String, ?>) c).delete(key);
		}
	}

	@SuppressWarnings("unchecked")
	protected Result doShow(String model, String key) {
		CRUD<? extends Serializable, ?> c = models.get(model);
		try {
			Long intKey = Long.getLong(key);
			return ((CRUD<Long, ?>) c).get(intKey);
		} catch (Exception e) {
			return ((CRUD<String, ?>) c).get(key);
		}
	}

}
