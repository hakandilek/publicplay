package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Page extends Controller {

	public Result about() {
		return ok(views.html.page.about.render());
	}
	
	public Result faq() {
		return ok(views.html.page.faq.render());
	}
	
	public Result policy() {
		return ok(views.html.page.policy.render());
	}

}
