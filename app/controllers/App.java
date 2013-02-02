package controllers;


import javax.inject.Inject;

import play.mvc.Result;
import security.CommentDeletePermission;
import security.CommentEditPermission;
import security.PostDeletePermission;
import security.PostEditPermission;
import security.RestrictApproved;
import security.RestrictCombine;
import socialauth.core.Secure;
import socialauth.core.SocialAware;
import controllers.crud.RateController;

public class App extends Admin {

	@Inject static RateController rate;

	@SocialAware public static Result index() {
		return post.list(0);
	}
	
	@SocialAware
	public static Result postList(int page) {
		return post.list(page);
	}
	
	@Secure
	@RestrictApproved
	public static Result postNewForm() {
		return post.newForm();
	}

	@Secure
	@RestrictApproved
	public static Result postCreate() {
		return post.create();
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public static Result postEditForm(Long key) {
		return post.editForm(key);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostEditPermission.class)
	@RestrictApproved
	public static Result postUpdate(Long key) {
		return post.update(key);
	}

	@SocialAware
	public static Result postShow(Long postKey, String title, int page) {
		return post.show(postKey, title, page);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = PostDeletePermission.class)
	@RestrictApproved
	public static Result postDelete(Long key) {
		return post.delete(key);
	}

	@Secure
	@RestrictApproved
	public static Result commentCreate(Long postKey, String title) {
		return comment.create(postKey, title);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result commentEditForm(Long postKey, Long commentKey) {
		return comment.editForm(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentEditPermission.class)
	@RestrictApproved
	public static Result commentUpdate(Long postKey, Long commentKey) {
		return comment.update(postKey, commentKey);
	}

	@Secure
	@RestrictCombine(roles = "admin", with = CommentDeletePermission.class)
	@RestrictApproved
	public static Result commentDelete(Long postKey, Long commentKey) {
		return comment.delete(postKey, commentKey);
	}

	@Secure
	@RestrictApproved
	public static Result rateUp(Long key) {
		return rate.rateUp(key);
	}

	@Secure
	@RestrictApproved
	public static Result rateDown(Long key) {
		return rate.rateDown(key);
	}
	
	public static Result rateShow(Long postKey) {
		return rate.rateShow(postKey);
	}


	/*
	@Secure @Restrict("admin") @RestrictApproved public static Result fetch() {
		return fetch.newLink();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result fetchCreate() {
		return fetch.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result fetchDelete(Long key) {
		return fetch.delete(key);
	}

	 */
}