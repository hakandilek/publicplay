package controllers;


import javax.inject.Inject;

import be.objectify.deadbolt.java.actions.SubjectPresent;

import play.mvc.Controller;
import play.mvc.Result;
import security.Authenticated;
import security.CommentDeletePermission;
import security.CommentEditPermission;
import security.PostDeletePermission;
import security.PostEditPermission;
import security.RestrictApproved;
import security.RestrictCombine;


public class App extends Controller {

	@Inject RateController rateController;
	@Inject AuthController authController;
	@Inject PostController postController;
	@Inject CommentController commentController;
	@Inject UserController userController;
	@Inject ContactPageController contactPageController;

	public Result index() {
		return postController.list(0, null);
	}
	
	public Result postList(int page, String category) {
		return postController.list(page, category);
	}
	
	@Authenticated
	@RestrictApproved
	public Result postNewForm() {
		return postController.newForm();
	}

	@Authenticated
	@RestrictApproved
	public Result postCreate() {
		return postController.create();
	}
	
	@Authenticated
	@RestrictApproved
	public Result postListFollowing(int page) {
		return postController.listFollowing(page);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public Result postUpdate(Long key) {
		return postController.update(key);
	}

	public Result postShow(Long postKey, String title, int page) {
		return postController.show(postKey, title, page);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = PostDeletePermission.class)
	@RestrictApproved
	public Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Authenticated
	@RestrictApproved
	public Result commentCreate(Long postKey, String title) {
		return commentController.create(postKey, title);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public Result commentEditForm(Long postKey, Long commentKey) {
		return commentController.editForm(postKey, commentKey);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public Result commentUpdate(Long postKey, Long commentKey) {
		return commentController.update(postKey, commentKey);
	}

	@Authenticated
	@RestrictCombine(roles = "admin", with = CommentDeletePermission.class)
	@RestrictApproved
	public Result commentDelete(Long postKey, Long commentKey) {
		return commentController.delete(postKey, commentKey);
	}

	@Authenticated
	@RestrictApproved
	public Result rateUp(Long key) {
		return rateController.rateUp(key);
	}

	@Authenticated
	@RestrictApproved
	public Result rateDown(Long key) {
		return rateController.rateDown(key);
	}
	
	public Result rateShow(Long postKey) {
		return rateController.rateShow(postKey);
	}

	@RestrictApproved
	public Result userShow(String key,String tab,int votedPageNumber) {
		return userController.show(key,tab,votedPageNumber);
	}
	
	@RestrictApproved
	public Result userFollowers(String key,int pageNumber) {
		return userController.showFollowers(key,pageNumber);
	}
	
	@RestrictApproved
	public Result userFollowings(String key,int pageNumber) {
		return userController.showFollowings(key,pageNumber);
	}

	@Authenticated
	public Result userShowSelf() {
		return userController.showSelf();
	}

	public Result login() {
		return authController.login();
	}

	public Result logout() {
		return authController.logout();
	}

	public Result authenticate(String provider) {
		return authController.authenticate(provider);
	}

	public Result authenticateDenied(String provider) {
		return authController.authenticateDenied(provider);
	}
	
	public Result contactNewForm() {
		return contactPageController.newForm();
	}
	
	public Result contact(){
		return contactPageController.contact();
	}

}
