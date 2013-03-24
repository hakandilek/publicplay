package controllers;

import static play.data.Form.form;

import java.util.List;
import java.util.Map;

import models.User;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.contact;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

public class ContactPageController extends Controller {

	/**
	 * Defines a form wrapping the User class.
	 */
	private Form<ContactPage> contactForm = form(ContactPage.class);
	
	private static ALogger log = Logger.of(PostController.class);

	
	public Result newForm() {
		if (log.isDebugEnabled())
			log.debug("newForm() <-");
		User user = HttpUtils.loginUser();
		if(null==user){
			return ok(contact.render(contactForm));
		}else{
			ContactPage contactPage = new ContactPage(user.getFirstName()+" "+user.getLastName(), user.getEmail(), null);
			return ok(contact.render(contactForm.fill(contactPage)));
		}
		
	}
	
	public Result contact() {
		Form<ContactPage> filledForm = contactForm.bindFromRequest();
		
		if (filledForm.hasErrors()) {

			Map<String, List<ValidationError>> errors = filledForm.errors();
			if (log.isDebugEnabled())
				log.debug("errors : " + errors);
			return badRequest(contact.render(filledForm));
		} else {
			ContactPage contactPage = filledForm.get();
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject(contactPage.getName());
			mail.addRecipient("necipk@gmail.com");
			mail.addFrom(contactPage.getEmail());
			//sends text/text
			mail.send(contactPage.getMessage());
			flash("success","hellp");
			return ok(contact.render(contactForm));
		}
	}

}
