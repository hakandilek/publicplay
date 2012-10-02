package crud.controllers;

import java.util.ArrayList;
import java.util.List;

import models.CRUDModel;

import play.db.ebean.Model;

import com.avaje.ebean.Page;

public class MetaPage<T extends Model> implements Page<CRUDModel> {

	private Page<T> page;
	private List<CRUDModel> list;
	private String keyFieldName;
	private List<String> fieldNames;

	public MetaPage(Page<T> page, String keyFieldName, List<String> fieldNames) {
		this.page = page;
		this.keyFieldName = keyFieldName;
		this.fieldNames = fieldNames;
		this.list = new ArrayList<CRUDModel>();
		List<T> l = page.getList();
		for (T t : l) {
			list.add(new CRUDModel(t, keyFieldName, fieldNames));
		}
	}

	@Override
	public List<CRUDModel> getList() {
		return list;
	}

	@Override
	public int getTotalRowCount() {
		return page.getTotalRowCount();
	}

	@Override
	public int getTotalPageCount() {
		return page.getTotalPageCount();
	}

	@Override
	public int getPageIndex() {
		return page.getPageIndex();
	}

	@Override
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	public boolean hasPrev() {
		return page.hasPrev();
	}

	@Override
	public Page<CRUDModel> next() {
		return new MetaPage<T>(page.next(), keyFieldName, fieldNames);
	}

	@Override
	public Page<CRUDModel> prev() {
		return new MetaPage<T>(page.prev(), keyFieldName, fieldNames);
	}

	@Override
	public String getDisplayXtoYofZ(String to, String of) {
		return page.getDisplayXtoYofZ(to, of);
	}

}
