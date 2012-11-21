package security;

import java.util.regex.Pattern;

import models.Comment;

public class CommentEditPermission extends EntityRequestPermission {

	public CommentEditPermission() {
		super(Comment.class, "edit", Pattern.compile("/posts/.*/comments/(.*)/edit"), 
									Pattern.compile("/posts/.*/comments/(.*)"));
	}
}
