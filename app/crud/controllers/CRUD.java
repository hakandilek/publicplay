package crud.controllers;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import models.CRUDForm;
import models.CRUDModel;
import play.Logger;
import play.Logger.ALogger;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.mvc.Result;
import views.html.crud.editForm;
import views.html.crud.list;
import views.html.crud.show;

import com.avaje.ebean.Page;

import controllers.routes;

public class CRUD<K extends Serializable, T extends Model> {

	private static final int PAGE_SIZE = 10;

	private static ALogger log = Logger.of(CRUD.class);

	private final Model.Finder<K, T> find;
	private final CRUDForm form;
	private final String name;
	private final List<String> fieldNames;
	private final String keyFieldName;

	public CRUD(Class<T> cls, Finder<K, T> find) {
		super();
		this.name = cls.getSimpleName();
		this.find = find;
		this.fieldNames = new ArrayList<String>();
		String keyField = null;
        for(Field f:cls.getDeclaredFields()) {
            if(Utils.isFieldAnnotated(f))
                fieldNames.add(f.getName());
            if (Utils.isKeyField(f))
            	keyField = f.getName();
        }
        this.keyFieldName = keyField; 
		this.form = new CRUDForm(fieldNames);
	}
	
	public String getName() {
		return name;
	}

	public Result list(int page) {
		if (log.isDebugEnabled())
			log.debug("list() <-");
		Page<T> p = find.where().findPagingList(PAGE_SIZE).getPage(page);
		Page<CRUDModel> metaPage = new MetaPage<T>(p, keyFieldName, fieldNames);
		return ok(list.render(metaPage, name, keyFieldName, fieldNames));
	}

	public Result newForm() {
		return ok(editForm.render(name, null, form));
	}

	public Result editForm(K key) {
		T t = find.byId(key);
		if (t == null) {
			return notFound();
		}
		final CRUDModel model = new CRUDModel(t, keyFieldName, fieldNames);
		CRUDForm frm = form.fillForm(model);
		return ok(editForm.render(name, key, frm));
	}

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		CRUDForm createForm = form.bindFromRequest();
		if (createForm.hasErrors()) {
			return badRequest();
		} else {
			createForm.get().save();
			return redirect(routes.AdminController.list(name, 0));
		}
	}

	public Result update(K key) {
		CRUDForm filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(editForm.render(name, key, filledForm));
		} else {
			CRUDModel model = filledForm.get();
			model.update(key);
			return redirect(routes.AdminController.list(name, 0));
		}
	}

	public Result get(K key) {
		if (log.isDebugEnabled())
			log.debug("get() <-" + key);
		T t = find.byId(key);
		if (t == null) {
			return notFound();
		}
		return ok(show.render(name, new CRUDModel(t, keyFieldName, fieldNames)));
	}

	public Result delete(K key) {
		find.ref(key).delete();
		return redirect(routes.AdminController.list(name, 0));
	}

}
