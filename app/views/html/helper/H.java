package views.html.helper;

import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

import play.api.templates.Html;


public class H {

	//TODO: use request locale
	private static PrettyTime prettyTime = new PrettyTime(Locale.ENGLISH);
	
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
	
	public static Html prettify(Date d) {
		if (d == null)
			return new Html("");
		String s = prettyTime.format(d);
		return new Html(s);
	}
	
	public static String sanitize(String s) {
		if (s == null)
			return "";
		s = s.replaceAll("[^A-Za-z0-9]", "_");
		s = s.toLowerCase(Locale.ENGLISH);
		return s;
	}
	
	
}
