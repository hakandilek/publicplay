package controllers;

import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Result;
import play.utils.crud.APIController;
import security.Authenticated;
import security.RestrictApproved;

import be.objectify.deadbolt.java.actions.*;

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

	@Inject ProductReader productReader;
	@Inject CategoryAPIController categoryAPI;
	@Inject CommentAPIController commentAPI;
	@Inject PostAPIController postAPI;
	@Inject PostRatingAPIController postRatingAPI;
	@Inject S3FileAPIController s3FileAPI;
	@Inject SecurityRoleAPIController securityRoleAPI;
	@Inject UserAPIController userAPI;
	@Inject SourceConfigurationAPIController sourceConfigurationAPI;
	@Inject ContentReportAPIController contentReportAPI;
	
	@Authenticated
	@BodyParser.Of(Json.class)
	public Result invalid(String resource) {
		return APIController.invalid(resource);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result categoryUpdate(String key) {
		return categoryAPI.update(key);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result commentUpdate(Long key) {
		return commentAPI.update(key);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result postUpdate(Long key) {
		return postAPI.update(key);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result postRatingUpdate(String key) {
		return postRatingAPI.update(PostRatingPK.fromString(key));
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result s3FileUpdate(String key) {
		return s3FileAPI.update(UUID.fromString(key));
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result securityRoleUpdate(Long key) {
		return securityRoleAPI.update(key);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result securityRoleList() {
		return securityRoleAPI.list();
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result userUpdate(String key) {
		return userAPI.update(key);
	}
	
	@Authenticated
	@BodyParser.Of(Json.class)
	public Result userRoleUpdate(String key) {
		return userAPI.roleUpdate(key);
	}
	
	@Authenticated
	@BodyParser.Of(Json.class)
	public Result sourceConfigurationUpdate(Long key) {
		return sourceConfigurationAPI.update(key);
	}

	@RestrictApproved
	public Result userFollow(String key) {
		return userAPI.follow(key);
	}

	@RestrictApproved
	public Result userUnfollow(String key) {
		return userAPI.unfollow(key);
	}

	@Authenticated
	@BodyParser.Of(Json.class)
	public Result contentReportCreate() {
		return contentReportAPI.create();
	}
	
	@Authenticated
	@BodyParser.Of(Json.class)
	public Result contentReportUpdate(Long key) {
		return contentReportAPI.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result contentReportIgnore(Long key) {
		return contentReportAPI.ignore(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result postApprove(Long key) {
		return postAPI.approve(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result postRemove(Long key) {
		return postAPI.remove(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result postMarkExpired(Long key) {
		return postAPI.expire(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result commentApprove(Long key) {
		return commentAPI.approve(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved 
	@BodyParser.Of(Json.class)
	public Result commentRemove(Long key) {
		return commentAPI.remove(key);
	}
}
