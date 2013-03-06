package controllers;


import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;
import security.CommentDeletePermission;
import security.CommentEditPermission;
import security.PostDeletePermission;
import security.PostEditPermission;
import security.RestrictApproved;
import security.RestrictCombine;
import socialauth.core.Secure;
import socialauth.core.SocialAware;

public class App extends Controller {

	@Inject static RateController rateController;
	@Inject static SocialController socialController;
	@Inject static PostController postController;
	@Inject static CommentController commentController;
	@Inject static UserController userController;

	@SocialAware public static Result index() {
		return postController.list(0, null);
	}
	
	@SocialAware
	public static Result postList(int page, String category) {
		return postController.list(page, category);
	}
	
	@Secure
	@RestrictApproved
	public static Result postNewForm() {
		return postController.newForm();
	}

	@Secure
	@RestrictApproved
	public static Result postCreate() {
		return postController.create();
	}
	
	@Secure
	@RestrictApproved
	public static Result postListFollowing(int page) {
		return postController.listFollowing(page);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public static Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public static Result postUpdate(Long key) {
		return postController.update(key);
	}

	@SocialAware
	public static Result postShow(Long postKey, String title, int page) {
		return postController.show(postKey, title, page);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostDeletePermission.class)
	@RestrictApproved
	public static Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Secure
	@RestrictApproved
	public static Result commentCreate(Long postKey, String title) {
		return commentController.create(postKey, title);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result commentEditForm(Long postKey, Long commentKey) {
		return commentController.editForm(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result commentUpdate(Long postKey, Long commentKey) {
		return commentController.update(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentDeletePermission.class)
	@RestrictApproved
	public static Result commentDelete(Long postKey, Long commentKey) {
		return commentController.delete(postKey, commentKey);
	}

	@Secure
	@RestrictApproved
	public static Result rateUp(Long key) {
		return rateController.rateUp(key);
	}

	@Secure
	@RestrictApproved
	public static Result rateDown(Long key) {
		return rateController.rateDown(key);
	}
	
	public static Result rateShow(Long postKey) {
		return rateController.rateShow(postKey);
	}

	@SocialAware
	@RestrictApproved
	public static Result userShow(String key,String tab,int votedPageNumber) {
		return userController.show(key,tab,votedPageNumber);
	}
	
	@SocialAware
	@RestrictApproved
	public static Result userFollowers(String key,int pageNumber) {
		return userController.showFollowers(key,pageNumber);
	}
	
	@SocialAware
	@RestrictApproved
	public static Result userFollowings(String key,int pageNumber) {
		return userController.showFollowings(key,pageNumber);
	}

	@Secure
	public static Result userShowSelf() {
		return userController.showSelf();
	}

	public static Result login() {
		return socialController.login();
	}

	public static Result logout() {
		return socialController.logout();
	}

	public static Result authenticate(String provider) {
		return socialController.authenticate(provider);
	}

	public static Result authenticateDone(String provider) {
		return socialController.authenticateDone(provider);
	}

}