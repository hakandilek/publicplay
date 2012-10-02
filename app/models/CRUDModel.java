package models;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import play.db.ebean.Model;

public class CRUDModel {

	private final Model model;
	private final Map<String, Object> fieldValues;
	private final String keyFieldName;

	public CRUDModel(Model model, String keyFieldName, List<String> fieldNames) {
		this.model = model;
		this.keyFieldName = keyFieldName;
		this.fieldValues = new TreeMap<String, Object>();
		for (int i = 0; i < fieldNames.size(); i++) {
			String name = fieldNames.get(i);
			Object value = model._ebean_getField(i, model);
			fieldValues.put(name, value );
		}
	}

	public Model getModel() {
		return model;
	}

	public Object getField(String name) {
		return fieldValues.get(name);
	}
	
	public String getKey() {
		return "" + fieldValues.get(keyFieldName);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(model.getClass().getSimpleName())
				.append(":").append(model).append("]");
		return builder.toString();
	}

}
