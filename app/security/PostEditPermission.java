package security;

import java.util.regex.Pattern;

import models.Post;

public class PostEditPermission  extends EntityRequestPermission {

	public PostEditPermission() {
		super(Pattern.compile("/posts/(.*)/edit"), Post.class, "edit");
	}

}
