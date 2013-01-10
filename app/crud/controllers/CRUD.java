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
import play.mvc.Http.Context;
import play.mvc.Result;

import com.avaje.ebean.Page;

import controllers.routes;

public class CRUD<K extends Serializable, T extends Model> {

	public static final int PAGE_SIZE = 10;

	private static ALogger log = Logger.of(CRUD.class);

	private final Model.Finder<K, T> find;
	private final CRUDForm form;
	private final String name;
	private final List<String> fieldNames;
	private final String keyFieldName;

	private final Class<T> clazz;

	private final CRUDPage<K> page;

	public CRUD(Class<T> cls, Finder<K, T> find, CRUDPage<K> page) {
		super();
		this.clazz = cls;
		this.page = page;
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
		this.form = new CRUDForm(clazz, keyFieldName, fieldNames);
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getFieldNames() {
		return fieldNames;
	}

	public String getKeyFieldName() {
		return keyFieldName;
	}
	
	public CRUDPage<K> getPage() {
		return page;
	}

	public Result list(int page, Context context) {
		if (log.isDebugEnabled())
			log.debug("list() <-");
		Page<T> p = find.where().findPagingList(PAGE_SIZE).getPage(page);
		Page<CRUDModel> metaPage = new MetaPage<T>(p, keyFieldName, fieldNames);
		return ok(this.page.renderListPage(context, name, metaPage, keyFieldName, fieldNames));
	}

	public Result newForm(Context context) {
		return ok(this.page.renderEditPage(context, name, null, keyFieldName, form));
	}

	public Result editForm(K key, Context context) {
		if (log.isDebugEnabled())
			log.debug("editForm() <-" + key);
		T t = find.byId(key);
		if (log.isDebugEnabled())
			log.debug("t : " + t);
		if (t == null) {
			return notFound();
		}
		final CRUDModel model = new CRUDModel(t, keyFieldName, fieldNames);
		CRUDForm frm = form.fillForm(model);
		return ok(this.page.renderEditPage(context, name, key, keyFieldName, frm));
	}

	public Result create(Context context) {
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

	public Result update(K key, Context context) {
		CRUDForm filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");
			
			return badRequest(this.page.renderEditPage(context, name, key, keyFieldName, filledForm));
		} else {
			CRUDModel model = filledForm.get();
			model.update(key);
			return redirect(routes.AdminController.list(name, 0));
		}
	}

	public Result get(K key, Context context) {
		if (log.isDebugEnabled())
			log.debug("get() <-" + key);
		T t = find.byId(key);
		if (t == null) {
			return notFound();
		}
		return ok(this.page.renderShowPage(context, name, new CRUDModel(t, keyFieldName, fieldNames)));
	}

	public Result delete(K key, Context context) {
		find.ref(key).delete();
		return redirect(routes.AdminController.list(name, 0));
	}

}
