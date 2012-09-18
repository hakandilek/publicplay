package views.html.helper;

import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

import play.api.templates.Html;

/**
 * HTML Utils
 * 
 * @author hakan
 */
public class H {

	// TODO: use request locale
	private static PrettyTime prettyTime = new PrettyTime(Locale.ENGLISH);

	/**
	 * replace NLs in text with HTML br tags
	 * 
	 * @param s
	 *            text to replace
	 * @return HTML output
	 */
	public static Html nl2br(String s) {
		if (s == null)
			return new Html("");
		s = s.replaceAll("\n\r", "<br/>");
		s = s.replaceAll("\r", "<br/>");
		s = s.replaceAll("\n", "<br/>");
		return new Html(s);
	}

	/**
	 * textual representation of the given object
	 * 
	 * @param s
	 *            object to display
	 * @return HTML output
	 */
	public static Html string(Object s) {
		return new Html("" + s);
	}

	/**
	 * prettifies the given date using Prettytime library
	 * 
	 * @param d
	 *            date to show
	 * @return HTML output
	 */
	public static Html prettify(Date d) {
		if (d == null)
			return new Html("");
		String s = prettyTime.format(d);
		return new Html(s);
	}

	/**
	 * Sanitizes and replaces the given text to be used in an URL
	 * 
	 * @param s
	 *            text to replace
	 * @return HTML output
	 */
	public static String sanitize(String s) {
		if (s == null)
			return "";
		s = s.replaceAll("[^A-Za-z0-9]", "_");
		s = s.toLowerCase(Locale.ENGLISH);
		return s;
	}

}
