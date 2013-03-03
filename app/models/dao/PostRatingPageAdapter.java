package models.dao;

import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.PostRating;

import com.avaje.ebean.Page;

public class PostRatingPageAdapter extends PageAdapter<PostRating, Post> {

	private final PostDAO postDAO;

	public PostRatingPageAdapter(Page<PostRating> delegate, PostDAO postDAO) {
		super(delegate);
		this.postDAO = postDAO;
	}

	@Override
	public List<Post> getList() {
		List<PostRating> ratingList = delegate.getList();
		List<Post> list = new ArrayList<Post>();
		for (PostRating rating : ratingList) {
			Long postKey = rating.getKey().getPostKey();
			Post post = postDAO.get(postKey);
			if (post != null)
				list.add(post);			
		}
		return list;
	}

	@Override
	public PageAdapter<PostRating, Post> create(Page<PostRating> delegate) {
		return new PostRatingPageAdapter(delegate, postDAO);
	}

}
