package security;

import models.Post;

public class PostEditPermission extends EntityPermission<Post, Long> {

	public PostEditPermission(Long key) {
		super(Post.class, "edit", key);
	}

}
