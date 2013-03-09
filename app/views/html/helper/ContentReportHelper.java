package views.html.helper;

import java.util.ArrayList;
import java.util.List;

import models.ContentReport;
import models.ContentReport.Reason;
import play.data.Form;
import play.mvc.Controller;

public class ContentReportHelper {

	private static List<String> commentReasons;
	private static List<String> postReasons;

	public static Form<ContentReport> reportForm() {
		Form<ContentReport> form = Controller.form(ContentReport.class);
		return form;
	}
	
	public static List<String> postReasons() {
		if (postReasons == null) {
			postReasons = new ArrayList<String>();
			Reason[] all = Reason.values();
			for (Reason r : all) {
				postReasons.add(r.name());
			}
		}
		return postReasons;
	}

	public static List<String> commentReasons() {
		if (commentReasons == null) {
			commentReasons = new ArrayList<String>();
			Reason[] all = Reason.values();
			for (Reason r : all) {
				if (r != Reason.EXPIRED)
					commentReasons.add(r.name());
			}
		}
		return commentReasons;
	}
}
