package crud.controllers;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import models.CRUDModel;

import com.avaje.ebean.Page;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.mvc.Result;
import views.html.crud.*;

import static play.mvc.Controller.*;

@SuppressWarnings("unchecked")
public class CRUD<K extends Serializable, T extends Model> {

	private static Class<Annotation>[] ANNOTATION_CLASSES = new Class [] {
		javax.persistence.Id.class,
		javax.persistence.EmbeddedId.class,
		javax.persistence.Basic.class,
		play.data.validation.Constraints.Required.class,
	};
	
	private static Class<Annotation>[] KEY_ANNOTATION_CLASSES = new Class [] {
		javax.persistence.Id.class,
		javax.persistence.EmbeddedId.class,
	};
	
	String[] s = new String[]{
			
	};
	private static final int PAGE_SIZE = 10;

	private static ALogger log = Logger.of(CRUD.class);

	private final Model.Finder<K, T> find;
	private final Form<T> form;
	private final String name;
	private final List<String> fieldNames;
	private final String keyFieldName;

	public CRUD(Class<T> cls, Finder<K, T> find, Form<T> form) {
		super();
		this.name = cls.getSimpleName();
		this.find = find;
		this.form = form;
		this.fieldNames = new ArrayList<String>();
		String keyField = null;
        for(Field f:cls.getDeclaredFields()) {
            if(isFieldAnnotated(f))
                fieldNames.add(f.getName());
            if (isKeyField(f))
            	keyField = f.getName();
        }
        this.keyFieldName = keyField; 
	}
	
	private boolean isFieldAnnotated(Field f) {
		for (Class<Annotation> ann : ANNOTATION_CLASSES) {
			if (f.isAnnotationPresent(ann))
				return true;
		}
		return false;
	}

	private boolean isKeyField(Field f) {
		for (Class<Annotation> ann : KEY_ANNOTATION_CLASSES) {
			if (f.isAnnotationPresent(ann))
				return true;
		}
		return false;
	}


	public CRUD(Class<T> cls, Finder<K, T> find) {
		this(cls, find, form(cls));
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

	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create() <-");
		Form<T> createForm = form.bindFromRequest();
		if (createForm.hasErrors()) {
			return badRequest();
		} else {
			createForm.get().save();
			return ok();
		}
	}

	public Result get(K key) {
		if (log.isDebugEnabled())
			log.debug("get() <-" + key);
		T t = find.byId(key);
		if (t == null) {
			return notFound();
		}
		return ok();
	}

}
