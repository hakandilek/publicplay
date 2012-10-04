package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import play.Logger;
import play.Logger.ALogger;
import play.db.ebean.Model;

public class CRUDModel {

	private static ALogger log = Logger.of(CRUDModel.class);
	
	private final Model model;
	private final Map<String, Object> fieldValues;
	private final String keyFieldName;

	public CRUDModel() {
		this(null, null, new ArrayList<String>());
	}

	public CRUDModel(Model model, String keyFieldName, List<String> fieldNames) {
		this.model = model;
		this.keyFieldName = keyFieldName + "";
		this.fieldValues = new TreeMap<String, Object>();
		if (model != null) {
			final String[] fields = model._ebean_getFieldNames();
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i];
				if (fieldNames.contains(name)) {
					Object value = model._ebean_getField(i, model);
					fieldValues.put(name, value);
				}
			}
		}
	}

	public Model getModel() {
		return model;
	}

	public Collection<String> getFields() {
		return fieldValues.keySet();
	}

	public Object getField(String name) {
		return fieldValues.get(name);
	}

	public String getKey() {
		return "" + fieldValues.get(keyFieldName);
	}

	public boolean isKeyField(String field)
	{
		return keyFieldName.equals(field); 
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(model == null ? null : model.getClass().getSimpleName())
				.append(":").append(model).append("]");
		return builder.toString();
	}

	public void save() {
		model.save();
	}

	public void update(Object key) {
		if (log.isDebugEnabled())
			log.debug("key : " + key + "(" + key.getClass() + ")");
		model.update(key);
	}

}
