package models;

import static play.libs.F.None;
import static play.libs.F.Some;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.ebean.Model;
import play.libs.F.Option;

public class CRUDForm extends Form<CRUDModel> {

	private static ALogger log = Logger.of(CRUDForm.class);
	
	private final List<String> fieldNames;

	private final String keyFieldName;

	private final Class<?> modelClass;


	/**
	 * Creates a new CRUD form.
	 * 
	 * @param data
	 *            the current form data (used to display the form)
	 * @param errors
	 *            the collection of errors associated with this form
	 * @param value
	 *            optional concrete value if the form submission was successful
	 */
	public CRUDForm(Class<?> modelClass, String keyFieldName, List<String> fieldNames, Map<String, String> data,
			Map<String, List<ValidationError>> errors, Option<CRUDModel> value) {
		super(null, CRUDModel.class, data, errors, value);
		this.modelClass = modelClass;
		this.keyFieldName = keyFieldName;
		this.fieldNames = fieldNames;
	}

	@SuppressWarnings("unchecked")
	public CRUDForm(Class<?> modelClass, String keyFieldName, List<String> fieldNames) {
		this(modelClass, keyFieldName, fieldNames, new HashMap<String, String>(), new HashMap<String, List<ValidationError>>(), None());
	}
	
	/**
	 * Gets the concrete value if the submission was a success.
	 */
	public String get(String key) {
		return (String) get().getField(key);
	}

	
	public List<String> getFieldNames() {
		return fieldNames;
	}


	/**
	 * Binds request data to this form - that is, handles form submission.
	 * 
	 * @return a copy of this form filled with the new data
	 */
	public CRUDForm bindFromRequest() {
		if (log.isDebugEnabled())
			log.debug("bindFromRequest() <-");
		CRUDForm form = bind(requestData());
		if (log.isDebugEnabled())
			log.debug("form : " + form);
		return form;
	}

	/**
	 * Binds data to this form - that is, handles form submission.
	 * 
	 * @param data
	 *            data to submit
	 * @return a copy of this form filled with the new data
	 */
	public CRUDForm bind(Map<String, String> data) {
		if (log.isDebugEnabled())
			log.debug("bind() <-" + data);
		Map<String, String> newData = new HashMap<String, String>();
		for (String key : data.keySet()) {
			newData.put(key, data.get(key));
		}
		data = newData;
		if (log.isDebugEnabled())
			log.debug("data : " + data);
		Form<CRUDModel> form = super.bind(data);
		if (log.isDebugEnabled())
			log.debug("form : " + form);
		Option<CRUDModel> value = form.value();
		CRUDModel crudModel = value.get();
		if (log.isDebugEnabled())
			log.debug("crudModel : " + crudModel);
		if (crudModel == null || crudModel.getModel() == null) {
			try {
				Model entity = (Model) modelClass.newInstance();
				CRUDModel model = new CRUDModel(entity, keyFieldName, fieldNames);
				value = Some(model);
			} catch (Exception e) {
				log.error("cannot assign model instance", e);
			}
		}
		CRUDForm crudForm = new CRUDForm(modelClass, keyFieldName, fieldNames, form.data(), form.errors(), value);
		if (log.isDebugEnabled())
			log.debug("crudForm : " + crudForm);
		return crudForm;
	}

	public CRUDForm fillForm(CRUDModel model) {
		if (model == null) {
			throw new RuntimeException("Cannot fill a form with a null value");
		}
		final Map<String, String> data = new HashMap<String, String>();
		final Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
		Collection<String> fields = model.getFields();
		for (String field : fields) {
			Object value = model.getField(field);
			data.put(field, value + "");
		}
		
		CRUDForm form = new CRUDForm(modelClass, keyFieldName, fieldNames, data, errors, Some(model));
		if (log.isDebugEnabled())
			log.debug("form : " + form);
		return form;
	}

}
