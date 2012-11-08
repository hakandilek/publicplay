package views.html.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.ocpsoft.prettytime.PrettyTime;

import play.api.templates.Html;
import play.i18n.Lang;
import play.i18n.Messages;

import com.avaje.ebean.Page;

/**
 * HTML Utils
 * 
 * @author hakan
 */
public class H {

	private final static Map<String, PrettyTime> prettyTimes = new HashMap<String, PrettyTime>();
	private final static PrettyTime prettyTimeDefault = new PrettyTime(Locale.ENGLISH);
	
	private final static DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	
	static {
		prettyTimes.put(Locale.ENGLISH.getLanguage(), new PrettyTime(Locale.ENGLISH));
		prettyTimes.put(new Locale("tr").getLanguage(), new PrettyTime(new Locale("tr")));
	}

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
		String language = getLang().language();
		PrettyTime pt = prettyTimes.get(language);
		if (pt == null)
			pt = prettyTimeDefault;
		String s = pt.format(d);
		return new Html(s);
	}

	/**
	 * simplifies the given date using a simple format
	 * 
	 * @param d
	 *            date to show
	 * @return HTML output
	 */
	public static Html simplify(Date d) {
		if (d == null)
			return new Html("");
		String s = simpleDateFormat.format(d);
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
	
	public static <T> Html paging(Page<T> page) {
		if (page == null)
			return new Html("");
		Lang lang = getLang();
		int pageIndex = page.getPageIndex() + 1;
		int totalPage = page.getTotalPageCount();

		return new Html(Messages.get(lang, "displaying_num_of_num_pages", pageIndex, totalPage));
	}
	
	private static Lang getLang() {
        Lang lang = null;
        if(play.mvc.Http.Context.current.get() != null) {
            lang = play.mvc.Http.Context.Implicit.lang();
        } else {
            Locale defaultLocale = Locale.getDefault();
            lang = new Lang(new play.api.i18n.Lang(defaultLocale.getLanguage(), defaultLocale.getCountry()));
        }
        return lang;
	}
	

}
