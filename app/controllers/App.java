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

	@Inject RateController rateController;
	@Inject SocialController socialController;
	@Inject PostController postController;
	@Inject CommentController commentController;
	@Inject UserController userController;

	@SocialAware public Result index() {
		return postController.list(0, null);
	}
	
	@SocialAware
	public Result postList(int page, String category) {
		return postController.list(page, category);
	}
	
	@Secure
	@RestrictApproved
	public Result postNewForm() {
		return postController.newForm();
	}

	@Secure
	@RestrictApproved
	public Result postCreate() {
		return postController.create();
	}
	
	@Secure
	@RestrictApproved
	public Result postListFollowing(int page) {
		return postController.listFollowing(page);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public Result postUpdate(Long key) {
		return postController.update(key);
	}

	@SocialAware
	public Result postShow(Long postKey, String title, int page) {
		return postController.show(postKey, title, page);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostDeletePermission.class)
	@RestrictApproved
	public Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Secure
	@RestrictApproved
	public Result commentCreate(Long postKey, String title) {
		return commentController.create(postKey, title);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public Result commentEditForm(Long postKey, Long commentKey) {
		return commentController.editForm(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public Result commentUpdate(Long postKey, Long commentKey) {
		return commentController.update(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentDeletePermission.class)
	@RestrictApproved
	public Result commentDelete(Long postKey, Long commentKey) {
		return commentController.delete(postKey, commentKey);
	}

	@Secure
	@RestrictApproved
	public Result rateUp(Long key) {
		return rateController.rateUp(key);
	}

	@Secure
	@RestrictApproved
	public Result rateDown(Long key) {
		return rateController.rateDown(key);
	}
	
	public Result rateShow(Long postKey) {
		return rateController.rateShow(postKey);
	}

	@SocialAware
	@RestrictApproved
	public Result userShow(String key,String tab,int votedPageNumber) {
		return userController.show(key,tab,votedPageNumber);
	}
	
	@SocialAware
	@RestrictApproved
	public Result userFollowers(String key,int pageNumber) {
		return userController.showFollowers(key,pageNumber);
	}
	
	@SocialAware
	@RestrictApproved
	public Result userFollowings(String key,int pageNumber) {
		return userController.showFollowings(key,pageNumber);
	}

	@Secure
	public Result userShowSelf() {
		return userController.showSelf();
	}

	public Result login() {
		return socialController.login();
	}

	public Result logout() {
		return socialController.logout();
	}

	public Result authenticate(String provider) {
		return socialController.authenticate(provider);
	}

	public Result authenticateDone(String provider) {
		return socialController.authenticateDone(provider);
	}

}