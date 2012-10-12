package security;

import models.Comment;

public class CommentDeletePermission extends EntityPermission<Comment, Long> {

	public CommentDeletePermission(Long key) {
		super(Comment.class, "delete", key);
	}

}
