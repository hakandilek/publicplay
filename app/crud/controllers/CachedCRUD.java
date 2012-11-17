package crud.controllers;

import java.io.Serializable;

import play.db.ebean.Model;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.utils.cache.CachedFinder;

public class CachedCRUD<K extends Serializable, T extends Model> extends CRUD<K, T> {

	private CachedFinder<K, T> cachedFinder;

	public CachedCRUD(Class<T> cls, CachedFinder<K, T> find, CRUDPage<K> page) {
		super(cls, find, page);
		cachedFinder = find;
	}

	@Override
	public Result delete(K key, Context context) {
		final Result res = super.delete(key, context);
		cachedFinder.clean(key);
		return res;
	}
	
}
