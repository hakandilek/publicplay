package security;

import models.Comment;

public class CommentEditPermission extends EntityPermission<Comment, Long> {

	public CommentEditPermission(Long key) {
		super(Comment.class, "edit", key);
	}

}
