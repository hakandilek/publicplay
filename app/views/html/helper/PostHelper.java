package views.html.helper;

import java.util.Set;

import models.Post;

public class PostHelper {

	public static boolean postIsVoted(Post post, Set<Long> votedPostKeys) {
		if (post != null && votedPostKeys != null) {
			return votedPostKeys.contains(post.getKey());
		}
		return false;
	}
}
