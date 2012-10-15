package security;

import java.util.regex.Pattern;

import models.Post;

public class PostDeletePermission  extends EntityRequestPermission {

	public PostDeletePermission() {
		super(Pattern.compile("/posts/(.*)/delete"), Post.class, "delete");
	}

}
