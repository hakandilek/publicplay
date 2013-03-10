package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import models.Comment;
import models.Post;
import models.SecurityRole;
import models.User;
import models.UserFollow;
import models.dao.CommentDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import models.dao.SecurityRoleDAO;
import models.dao.UserDAO;
import models.dao.UserFollowDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.DynamicTemplateController;
import views.html.*;
import com.avaje.ebean.Page;

public class UserController extends DynamicTemplateController {

	private static ALogger log = Logger.of(UserController.class);
	private PostRatingDAO postRatingDAO;
	private UserDAO userDAO;
	private UserFollowDAO userFollowDAO;
	private SecurityRoleDAO securityRoleDAO;
	private PostDAO postDAO;
	private CommentDAO commentDAO;

	@Inject
	public UserController(UserDAO userDAO, PostRatingDAO postRatingDAO,
			UserFollowDAO userFollowDAO, SecurityRoleDAO securityRoleDAO,
			PostDAO postDAO, CommentDAO commentDAO) {
		this.userDAO = userDAO;
		this.postRatingDAO = postRatingDAO;
		this.userFollowDAO = userFollowDAO;
		this.securityRoleDAO = securityRoleDAO;
		this.postDAO = postDAO;
		this.commentDAO = commentDAO;
	}

	public Result show(String key,String tab,int votedPageNumber) {
		if (log.isDebugEnabled())
			log.debug("show() <- " + key);
		User loginUser = HttpUtils.loginUser();
		User userToShow = null;
		if (null != key)
			userToShow = userDAO.get(key);
		return show(loginUser, userToShow,tab, votedPageNumber);
	}

	public Result showSelf() {
		if (log.isDebugEnabled())
			log.debug("showSelf() <-");
		User loginUser = HttpUtils.loginUser(ctx());
		return show(loginUser, loginUser,null,0);
	}

	private Result show(User loginUser, User userToShow,String tab,int pageNumber) {

		Page<Post> postPage=null;
		Page<Comment> commentPage=null;
		
		Set<Long> upVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getUpVotedPostKeys(userToShow);
		Set<Long> downVotes = userToShow == null ? new TreeSet<Long>()
				: postRatingDAO.getDownVotedPostKeys(userToShow);

		List<SecurityRole> allRoles = securityRoleDAO.all();
		List<SecurityRole> userRoles = new ArrayList<SecurityRole>();
		
		if (log.isDebugEnabled())
			log.debug("user : " + loginUser);
		if (loginUser == null || userToShow == null) {
			return badRequest(userShow.render(userToShow, false,tab, upVotes,
					downVotes, false, 0, 0, postPage));
		}

		boolean selfPage = false;
		if (loginUser != null && userToShow != null
				&& (loginUser.getKey() + "").equals(userToShow.getKey())) {
			selfPage = true;
		}

		boolean following = false;
		if (loginUser != null && userToShow != null) {
			String sourceKey = loginUser.getKey();
			String targetKey = userToShow.getKey();
			UserFollow follow = userFollowDAO.get(sourceKey, targetKey);
			following = follow != null;
		}
		int followerCount = userFollowDAO.getFollowerCount(userToShow);
		int followingCount = userFollowDAO.getFollowingCount(userToShow);

		
		
		if(tab == null || tab.toString().equals("") || tab.toString().equals(Constants.POSTS)){
			postPage = postDAO.getPostsCreatedBy(new ArrayList<String>( Arrays.asList(userToShow.getKey())),
					pageNumber, Constants.POSTS_PER_PAGE);
			return ok(userShow.render(userToShow, selfPage,tab, upVotes, downVotes,
					following, followerCount, followingCount,postPage));
		}else if(tab.toString().equals(Constants.COMMENTS)){
			commentPage = commentDAO.getCommentsBy(userToShow.getKey(), pageNumber, Constants.COMMENTS_PER_PAGE);
			return ok(userShowComments.render(userToShow, selfPage,tab,
					following, followerCount, followingCount,commentPage));
		}else if(tab.toString().equals(Constants.ROLES)){
			userRoles = userToShow.getSecurityRoles();
			return ok(userShowRoles.render(userToShow, selfPage,
					following, followerCount, followingCount,allRoles, userRoles));
		}else if(tab.toString().equals(Constants.VOTED_POSTS)){
			postPage= postRatingDAO.getUpVotedPosts(userToShow, pageNumber);
			return ok(userShowVotedPages.render(userToShow, selfPage,tab, upVotes, downVotes,
					following, followerCount, followingCount,postPage));
		}
		

		return badRequest(userShow.render(userToShow, false,tab, upVotes,
				downVotes, false, 0, 0, postPage));
	}

	

	public Result showFollowers(String key,int page) {
		User userToShow = null;
		if (null != key)
			userToShow = userDAO.get(key);
		Page<User> pg=userFollowDAO.getFollowerUsers(userToShow, page);
		List<String> followingUserKeys = userFollowDAO.getAllFollowingsKeys(HttpUtils.loginUser());
		boolean isFollowingPage=false;
		
		return ok(userFollowShow.render(pg,userToShow,followingUserKeys,isFollowingPage));
	}

	public Result showFollowings(String key,int page) {
		User userToShow = null;
		if (null != key)
			userToShow = userDAO.get(key);
		Page<User> pg=userFollowDAO.getFollowingUsers(userToShow, page);
		List<String> followingUserKeys = userFollowDAO.getAllFollowingsKeys(HttpUtils.loginUser());
		boolean isFollowingPage=true;
		
		return ok(userFollowShow.render(pg,userToShow,followingUserKeys,isFollowingPage));
	}

}
