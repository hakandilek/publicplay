package security;

import java.util.regex.Pattern;

import models.Post;

public class PostDeletePermission  extends EntityRequestPermission {

	public PostDeletePermission() {
		super(Post.class, "delete", Pattern.compile("/posts/(.*)/delete"));
	}

}
