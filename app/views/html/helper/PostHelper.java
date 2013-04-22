package views.html.helper;

import static models.ContentStatus.EXPIRED;
import java.util.Set;

import play.api.templates.Html;

import models.Post;
import scala.collection.mutable.StringBuilder;

public class PostHelper {

	public static final String DEL1 = "<del>", DEL2 = "</del>";

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
	
	public static Html postTitle(Post post) {
		if (post == null) return new Html(new StringBuilder());
		StringBuilder sb = new StringBuilder();
		if (post.getStatus() == EXPIRED)
			sb.append(DEL1);
		sb.append(post.getTitle());
		if (post.getStatus() == EXPIRED)
			sb.append(DEL2);
		return new Html(sb);
	}

	public static Html postContent(Post post) {
		if (post == null) return new Html(new StringBuilder());
		StringBuilder sb = new StringBuilder();
		if (post.getStatus() == EXPIRED)
			sb.append(DEL1);
		sb.append(post.getContent());
		if (post.getStatus() == EXPIRED)
			sb.append(DEL2);
		return new Html(sb);
	}
	
	public static boolean isPostCreatedByLoginUser(Post post){
		return post.getCreatedBy().equals(UserHelper.loginUser());
	}
}
