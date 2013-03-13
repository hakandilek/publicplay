package controllers;

import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Result;
import play.utils.crud.APIController;
import security.RestrictApproved;
import socialauth.core.Secure;
import socialauth.core.SocialAware;

import be.objectify.deadbolt.actions.Restrict;

import com.pickleproject.shopping.ProductReader;

import controllers.crud.CategoryAPIController;
import controllers.crud.CommentAPIController;
import controllers.crud.ContentReportAPIController;
import controllers.crud.PostAPIController;
import controllers.crud.PostRatingAPIController;
import controllers.crud.S3FileAPIController;
import controllers.crud.SecurityRoleAPIController;
import controllers.crud.SourceConfigurationAPIController;
import controllers.crud.UserAPIController;

public class Api extends App {

	@Inject static ProductReader productReader;
	@Inject static CategoryAPIController categoryAPI;
	@Inject static CommentAPIController commentAPI;
	@Inject static PostAPIController postAPI;
	@Inject static PostRatingAPIController postRatingAPI;
	@Inject static S3FileAPIController s3FileAPI;
	@Inject static SecurityRoleAPIController securityRoleAPI;
	@Inject static UserAPIController userAPI;
	@Inject static SourceConfigurationAPIController sourceConfigurationAPI;
	@Inject static ContentReportAPIController contentReportAPI;
	
	@Secure
	@BodyParser.Of(Json.class)
	public static Result invalid(String resource) {
		return APIController.invalid(resource);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result categoryUpdate(String key) {
		return categoryAPI.update(key);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result commentUpdate(Long key) {
		return commentAPI.update(key);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result postUpdate(Long key) {
		return postAPI.update(key);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result postRatingUpdate(String key) {
		return postRatingAPI.update(PostRatingPK.fromString(key));
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result s3FileUpdate(String key) {
		return s3FileAPI.update(UUID.fromString(key));
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result securityRoleUpdate(Long key) {
		return securityRoleAPI.update(key);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result securityRoleList() {
		return securityRoleAPI.list();
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result userUpdate(String key) {
		return userAPI.update(key);
	}
	
	@Secure
	@BodyParser.Of(Json.class)
	public static Result userRoleUpdate(String key) {
		return userAPI.roleUpdate(key);
	}
	
	@Secure
	@BodyParser.Of(Json.class)
	public static Result sourceConfigurationUpdate(Long key) {
		return sourceConfigurationAPI.update(key);
	}

	@SocialAware
	@RestrictApproved
	public static Result userFollow(String key) {
		return userAPI.follow(key);
	}

	@SocialAware
	@RestrictApproved
	public static Result userUnfollow(String key) {
		return userAPI.unfollow(key);
	}

	@Secure
	@BodyParser.Of(Json.class)
	public static Result contentReportCreate() {
		return contentReportAPI.create();
	}
	
	@Secure
	@BodyParser.Of(Json.class)
	public static Result contentReportUpdate(Long key) {
		return contentReportAPI.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result contentReportIgnore(Long key) {
		return contentReportAPI.ignore(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result postApprove(Long key) {
		return postAPI.approve(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result postRemove(Long key) {
		return postAPI.remove(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result postMarkExpired(Long key) {
		return postAPI.expire(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result commentApprove(Long key) {
		return commentAPI.approve(key);
	}

	@Secure @Restrict("admin") @RestrictApproved 
	@BodyParser.Of(Json.class)
	public static Result commentRemove(Long key) {
		return commentAPI.remove(key);
	}
}
