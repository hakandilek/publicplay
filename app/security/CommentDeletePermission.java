package security;

import java.util.regex.Pattern;

import models.Comment;

public class CommentDeletePermission extends EntityRequestPermission {

	public CommentDeletePermission() {
		super(Comment.class, "delete", Pattern.compile("/posts/.*/comments/(.*)/delete"));
	}

}
