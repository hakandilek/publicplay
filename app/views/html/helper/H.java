package views.html.helper;


public class H {

	public static play.api.templates.Html nl2br(String s) {
		return new play.api.templates.Html(s != null ? s.replaceAll("\n", "<br/>") : "");
	}
	
}
