package forms;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

public class ContactPage {

	@Required
	@MaxLength(120)
	private String name;

	@Required
	@Email
	private String email;

	@Required
	@MaxLength(2000)
	private String message;

	public ContactPage() {
	}

	public ContactPage(String name, String email, String message) {
		this.setName(name);
		this.setEmail(email);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}