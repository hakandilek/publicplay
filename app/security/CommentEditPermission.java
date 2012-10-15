package security;

import java.util.regex.Pattern;

import models.Comment;

public class CommentEditPermission extends EntityRequestPermission {

	public CommentEditPermission() {
		super(Pattern.compile("/posts/.*/comments/(.*)/edit"), Comment.class, "edit");
	}
}
