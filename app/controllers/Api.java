package controllers;

import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Result;
import play.utils.crud.APIController;
import socialauth.core.Secure;

import com.pickleproject.shopping.ProductReader;

import controllers.crud.CategoryAPIController;
import controllers.crud.CommentAPIController;
import controllers.crud.PostAPIController;
import controllers.crud.PostRatingAPIController;
import controllers.crud.S3FileAPIController;
import controllers.crud.SecurityRoleAPIController;
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
	
	/*
	@Inject static LinkSourceAPIController linkSource;
	@Inject static ProductRecordAPIController productRecord;
	@Inject static ProductScrapAPIController productScrap;
	@Inject static SourceConfigurationAPIController sourceConfiguration;
	@Inject static FetchAPIController fetch;

	@BodyParser.Of(Json.class)
	public static Result linkSourceList() {
		return linkSource.list();
	}

	@BodyParser.Of(Json.class)
	public static Result linkSourceCreate() {
		return linkSource.create();
	}

	@BodyParser.Of(Json.class)
	public static Result linkSourceUpdate(Long key) {
		return linkSource.update(key);
	}

	@BodyParser.Of(Json.class)
	public static Result linkSourceGet(Long key) {
		return linkSource.get(key);
	}

	@BodyParser.Of(Json.class)
	public static Result linkSourceDelete(Long key) {
		return linkSource.delete(key);
	}

	@BodyParser.Of(Json.class)
	public static Result scrapGet(Long key) {
		return fetch.get(key);
	}

	@BodyParser.Of(Json.class)
	public static Result scrapCreate() {
		return fetch.create();
	}
	
	@BodyParser.Of(Json.class)
	public static Result productRecordList() {
		return productRecord.list();
	}

	@BodyParser.Of(Json.class)
	public static Result productRecordCreate() {
		return productRecord.create();
	}

	@BodyParser.Of(Json.class)
	public static Result productRecordUpdate(Long key) {
		return productRecord.update(key);
	}

	@BodyParser.Of(Json.class)
	public static Result productRecordGet(Long key) {
		return productRecord.get(key);
	}

	@BodyParser.Of(Json.class)
	public static Result productRecordDelete(Long key) {
		return productRecord.delete(key);
	}

	@BodyParser.Of(Json.class)
	public static Result productScrapUpdate(Long key) {
		return productScrap.update(key);
	}
	 */
	
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
	public static Result userUpdate(String key) {
		return userAPI.update(key);
	}
	
	@BodyParser.Of(Json.class)
	public static Result sourceConfigurationUpdate(Long key) {
		return sourceConfiguration.update(key);
	}


}
