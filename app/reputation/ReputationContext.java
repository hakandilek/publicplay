package reputation;

import models.Post;

public class ReputationContext {

	private Post post;

	public ReputationContext(Post post) {
		this.post = post;
	}

	public Post getPost() {
		return post;
	}

}
