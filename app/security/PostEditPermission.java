package security;

import java.util.regex.Pattern;

import models.Post;

public class PostEditPermission  extends EntityRequestPermission {

	public PostEditPermission() {
		super(Post.class, "edit", Pattern.compile("/posts/(.*)/edit"),
								Pattern.compile("/posts/(.*)/update"));
	}

}
