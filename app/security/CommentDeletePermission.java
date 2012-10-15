package security;

import java.util.regex.Pattern;

import models.Comment;

public class CommentDeletePermission extends EntityRequestPermission {

	public CommentDeletePermission() {
		super(Pattern.compile("/posts/.*/comments/(.*)/delete"), Comment.class, "delete");
	}

}
