package models;

import static play.libs.F.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.F.Option;

public class CRUDForm extends Form<CRUDModel> {

	private final List<String> fieldNames;

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
	public CRUDForm(List<String> fieldNames, Map<String, String> data,
			Map<String, List<ValidationError>> errors, Option<CRUDModel> value) {
		super(null, CRUDModel.class, data, errors, value);
		this.fieldNames = fieldNames;
	}

	@SuppressWarnings("unchecked")
	public CRUDForm(List<String> fieldNames) {
		this(fieldNames, new HashMap<String, String>(), new HashMap<String, List<ValidationError>>(), None());
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
		return bind(requestData());
	}

	/**
	 * Binds data to this form - that is, handles form submission.
	 * 
	 * @param data
	 *            data to submit
	 * @return a copy of this form filled with the new data
	 */
	public CRUDForm bind(Map<String, String> data) {

		{
			Map<String, String> newData = new HashMap<String, String>();
			for (String key : data.keySet()) {
				newData.put("data[" + key + "]", data.get(key));
			}
			data = newData;
		}

		Form<CRUDModel> form = super.bind(data);
		return new CRUDForm(fieldNames, form.data(), form.errors(), form.value());
	}

	/**
	 * Retrieves a field.
	 * 
	 * @param key
	 *            field name
	 * @return the field - even if the field does not exist you get a field
	 */
	public Field field(String key) {
		return super.field("data[" + key + "]");
	}

	public CRUDForm fillForm(CRUDModel model) {
		if (model == null) {
			throw new RuntimeException("Cannot fill a form with a null value");
		}
		final Map<String, String> data = new HashMap<String, String>();
		final Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
		return new CRUDForm(fieldNames, data, errors, Some(model));
	}

}
