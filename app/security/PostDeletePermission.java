package security;

import models.Post;

public class PostDeletePermission extends EntityPermission<Post, Long> {

	public PostDeletePermission(Long key) {
		super(Post.class, "delete", key);
	}

}
