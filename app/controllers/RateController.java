package controllers;

import javax.inject.Inject;

import models.Post;
import models.PostRating;
import models.PostRatingPK;
import models.User;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import views.html.partials.rate;

public class RateController extends Controller implements Constants {

	private ALogger log = Logger.of(RateController.class);
	
	PostDAO postDAO;
	PostRatingDAO postRatingDAO;

	private HttpUtils httpUtils;

	@Inject
	public RateController(PostDAO postDAO, PostRatingDAO postRatingDAO, HttpUtils httpUtils) {
		super();
		this.postDAO = postDAO;
		this.postRatingDAO = postRatingDAO;
		this.httpUtils = httpUtils;
	}

	/**
	 * rating is done via ajax, therefore return simply the eventual rate sum
	 */
	@Secure
	@RestrictApproved
	public Result rateUp(Long key) {
		if (log.isDebugEnabled())
			log.debug("rateUp <-" + key);
		return rate(key, 1);
	}

	@Secure
	@RestrictApproved
	public Result rateDown(Long key) {
		if (log.isDebugEnabled())
			log.debug("rateDown <-" + key);
		return rate(key, -1);
	}

	public Result rate(Long postKey, int rating) {
		if (log.isDebugEnabled())
			log.debug("rate <-" + postKey);
		if (log.isDebugEnabled())
			log.debug("rating : " + rating);

		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		User user = httpUtils.loginUser();
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		if (post != null && user != null) {
			// save/update rate
			if (post.getRating() == null)
				post.setRating(0);
			PostRating pr = postRatingDAO.get(user, post);
			if (log.isDebugEnabled())
				log.debug("pr : " + pr);

			PostRatingPK key = new PostRatingPK(user.getKey(), post.getKey());
			if (pr == null) {
				pr = new PostRating();
				pr.setValue(rating);
				pr.setKey(key);
				postRatingDAO.create(pr);
				post.setRating(post.getRating() + rating);
			} else {
				int ratingBefore = pr.getValue();
				pr.setValue(rating);
				postRatingDAO.update(key, pr);
				if (log.isDebugEnabled())
					log.debug("post.rating : " + post.getRating());
				if (log.isDebugEnabled())
					log.debug("pr.value    : " + ratingBefore);
				post.setRating(post.getRating() - ratingBefore + rating);
			}

			postRatingDAO.resetVotedPostKeyCache(user);

			if (log.isDebugEnabled())
				log.debug("updating post : " + post);
			postDAO.update(postKey, post);
			if (log.isDebugEnabled())
				log.debug("post : " + post);

			return ok(rate.render(post.getRating()));
		} else {
			if (log.isDebugEnabled())
				log.debug("no user");
			return TODO;
		}
	}

	public Result rateShow(Long postKey) {
		Post post = postDAO.get(postKey);
		if (log.isDebugEnabled())
			log.debug("post : " + post);

		if (post != null) {
			return ok(rate.render(post.getRating()));
		}

		if (log.isDebugEnabled())
			log.debug("no post");
		return TODO;
	}
}
