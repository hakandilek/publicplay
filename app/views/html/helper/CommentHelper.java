package views.html.helper;

import static models.ContentStatus.EXPIRED;
import static views.html.helper.PostHelper.*;
import models.Comment;
import play.api.templates.Html;
import scala.collection.mutable.StringBuilder;

public class CommentHelper {

	public static Html content(Comment c) {
		if (c == null) return new Html(new StringBuilder());
		StringBuilder sb = new StringBuilder();
		if (c.getStatus() == EXPIRED)
			sb.append(DEL1);
		sb.append(c.getContent());
		if (c.getStatus() == EXPIRED)
			sb.append(DEL2);
		return new Html(sb);
	}
}
