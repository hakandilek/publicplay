package models.dao;

import models.PostRating;
import models.PostRatingPK;
import play.utils.dao.CachedDAO;

public class PostRatingDAO extends CachedDAO<PostRatingPK, PostRating> {

	public PostRatingDAO() {
		super(PostRatingPK.class, PostRating.class);
	}

}
