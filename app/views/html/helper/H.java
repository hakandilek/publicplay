package views.html.helper;

import play.api.templates.Html;


public class H {

	public static Html nl2br(String s) {
		if (s == null)
			return new Html("");
		s = s.replaceAll("\n\r", "<br/>");
		s = s.replaceAll("\r", "<br/>");
		s = s.replaceAll("\n", "<br/>");
		return new Html(s);
	}
	
	public static Html string(Object s) {
		return new Html("" + s);
	}
	
}
