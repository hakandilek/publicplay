package views.html.helper;

import java.util.Set;

import models.Post;

public class PostHelper {

	public static boolean postIsVotedUp(Post post, Set<Long> upVotedPostKeys) {
		if (post != null && upVotedPostKeys != null) {
			return upVotedPostKeys.contains(post.getKey());
		}
		return false;
	}

	public static boolean postIsVotedDown(Post post, Set<Long> downVotedPostKeys) {
		if (post != null && downVotedPostKeys != null) {
			return downVotedPostKeys.contains(post.getKey());
		}
		return false;
	}
}
