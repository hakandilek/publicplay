package views.html.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import models.Post;
import models.User;

import org.ocpsoft.prettytime.PrettyTime;

import play.api.templates.Html;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.Http.Request;

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
	 * textual representation of the given object
	 * 
	 * @param s
	 *            object to display
	 * @return HTML output
	 */
	public static Html string(Object s) {
		return new Html(new StringBuilder(s + ""));
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
			return new Html(new StringBuilder());
		String language = getLang().language();
		PrettyTime pt = prettyTimes.get(language);
		if (pt == null)
			pt = prettyTimeDefault;
		String s = pt.format(d);
		return new Html(new StringBuilder(s));
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
			return new Html(new StringBuilder());
		String s = simpleDateFormat.format(d);
		return new Html(new StringBuilder(s));
	}

	/**
	 * Sanitizes and replaces the given text to be used in an URL
	 * 
	 * @param s
	 *            url to sanitize
	 * @return sanitized URL
	 */
	public static String sanitizeURL(String s) {
		if (s == null)
			return "";
		s = s.replaceAll("[\u00df]", "ss");
		s = s.replaceAll("[\u00E4\u00C4]", "ae");
		s = s.replaceAll("[\u011E\u011F]", "g");
		s = s.replaceAll("[\u015E\u015F]", "s");
		s = s.replaceAll("[\u00C7\u00E7]", "c");
		s = s.replaceAll("[\u00F6\u00D6]", "o");
		s = s.replaceAll("[\u00FC\u00DC]", "u");
		s = s.replaceAll("[\u20AC]", "euro");
		s = s.replaceAll("[\u0024]", "dollar");
		s = s.replaceAll("[^A-Za-z0-9]", "_");
		s = s.toLowerCase(Locale.ENGLISH);
		return s;
	}

	public static <T> Html paging(Page<T> page) {
		if (page == null)
			return new Html(new StringBuilder());
		Lang lang = getLang();
		int pageIndex = page.getPageIndex() + 1;
		int totalPage = page.getTotalPageCount();

		return new Html(new StringBuilder(Messages.get(lang, "displaying_num_of_num_pages", pageIndex, totalPage)));
	}

	public static String getProfileImageURLWithNormalSize(User user) {
		return user.getProfileImageURL() + "?type=normal";
	}

	public static String formatDateToDayAndYear(Date dateToFormat) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(dateToFormat);
	}

	public static String subStringWithGivenLength(String stringToCut, int length) {
		if (stringToCut.length() <= length) {
			return stringToCut;
		} else {
			return stringToCut.substring(0, length);
		}
	}

	public static String getKeywordsFromTopPage(Page<Post> topPostPage) {
		String keyword = null;
		for (Post post : topPostPage.getList()) {
			keyword += post.getTitle() + " ";
		}
		keyword += Messages.get("Project_definition_keywords");
		return subStringWithGivenLength(keyword.replace(" ", ","), 300);
	}

	private static Lang getLang() {
		Lang lang = null;
		if (play.mvc.Http.Context.current.get() != null) {
			lang = play.mvc.Http.Context.Implicit.lang();
		} else {
			Locale defaultLocale = Locale.getDefault();
			lang = new Lang(new play.api.i18n.Lang(defaultLocale.getLanguage(), defaultLocale.getCountry()));
		}
		return lang;
	}

	public static Html requestUrl() {
		StringBuilder sb = new StringBuilder();
		Request req = play.mvc.Http.Context.current().request();
		if (req != null) {
			sb.append("http://").append(req.host()).append(req.uri());
		}
		return new Html(sb);
	}
}
