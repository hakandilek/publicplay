package views.html.helper;

import static models.ContentStatus.*;

import java.util.ArrayList;
import java.util.List;

import models.ContentReport;
import models.ContentReport.Reason;
import models.ContentStatus;
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
	
	public static boolean approvePossible(ContentStatus s) {
		return s == NEW || s == UPDATED;
	}

	public static boolean removePossible(ContentStatus s) {
		return s == NEW || s == UPDATED || s == EXPIRED || s == APPROVED;
	}

	public static boolean expirePossible(ContentStatus s) {
		return s == NEW || s == UPDATED || s == APPROVED;
	}

}
