package controllers;


import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import models.dao.CategoryDAO;
import models.dao.CommentDAO;
import models.dao.PostDAO;
import models.dao.PostRatingDAO;
import models.dao.S3FileDAO;
import models.dao.SecurityRoleDAO;
import models.dao.SourceConfigurationDAO;
import models.dao.UserDAO;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import be.objectify.deadbolt.actions.Restrict;
import controllers.crud.CategoryController;
import controllers.crud.CommentController;
import controllers.crud.PostRatingController;
import controllers.crud.S3FileController;
import controllers.crud.SecurityRoleController;
import controllers.crud.SourceConfigurationController;

public class Admin extends Controller {

	public static final int PAGE_SIZE = 20;

	@Inject static CategoryDAO categoryDAO;
	@Inject static CommentDAO commentDAO;
	@Inject static PostDAO postDAO;
	@Inject static PostRatingDAO postRatingDAO;
	@Inject static S3FileDAO s3FileDAO;
	@Inject static SecurityRoleDAO securityRoleDAO;
	@Inject static UserDAO userDAO;
	@Inject static SourceConfigurationDAO sourceConfigurationDAO;
	
	@Inject public static HttpUtils httpUtils;
	@Inject static CategoryController category;
	@Inject static CommentController comment;
	@Inject static controllers.crud.PostController post;
	@Inject static PostRatingController postRating;
	@Inject static S3FileController s3File;
	@Inject static SecurityRoleController securityRole;
	@Inject static controllers.crud.UserController user;
	@Inject static SourceConfigurationController sourceConfiguration;

	@Secure @Restrict("admin") @RestrictApproved public static Result index() {
		return ok(views.html.admin.index.render());
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryList(int page) {
		return category.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryNewForm() {
		return category.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryCreate() {
		return category.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryEditForm(String key) {
		return category.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryUpdate(String key) {
		return category.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryDelete(String key) {
		return category.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryShow(String key) {
		return category.show(key);
	}
	
	@Secure @Restrict("admin") @RestrictApproved public static Result commentList(int page) {
		return comment.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentNewForm() {
		return comment.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentCreate() {
		return comment.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentEditForm(Long key) {
		return comment.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentUpdate(Long key) {
		return comment.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentDelete(Long key) {
		return comment.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentShow(Long key) {
		return comment.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postList(int page) {
		return post.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postNewForm() {
		return post.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postCreate() {
		return post.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postEditForm(Long key) {
		return post.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postUpdate(Long key) {
		return post.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postDelete(Long key) {
		return post.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postShow(Long key) {
		return post.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingList(int page) {
		return postRating.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingNewForm() {
		return postRating.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingCreate() {
		return postRating.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingEditForm(String key) {
		return postRating.editForm(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingUpdate(String key) {
		return postRating.update(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingDelete(String key) {
		return postRating.delete(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingShow(String key) {
		return postRating.show(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileList(int page) {
		return s3File.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileNewForm() {
		return s3File.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileCreate() {
		return s3File.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileEditForm(String key) {
		return s3File.editForm(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileUpdate(String key) {
		return s3File.update(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileDelete(String key) {
		return s3File.delete(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileShow(String key) {
		return s3File.show(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleList(int page) {
		return securityRole.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleNewForm() {
		return securityRole.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleCreate() {
		return securityRole.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleEditForm(Long key) {
		return securityRole.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleUpdate(Long key) {
		return securityRole.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleDelete(Long key) {
		return securityRole.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleShow(Long key) {
		return securityRole.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userList(int page) {
		return user.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userNewForm() {
		return user.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userCreate() {
		return user.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userEditForm(String key) {
		return user.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userUpdate(String key) {
		return user.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userDelete(String key) {
		return user.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userShow(String key) {
		return user.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationList(int page) {
		return sourceConfiguration.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationCreate() {
		return sourceConfiguration.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationNewForm() {
		return sourceConfiguration.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationEditForm(Long key) {
		return sourceConfiguration.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationUpdate(Long key) {
		return sourceConfiguration.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationDelete(Long key) {
		return sourceConfiguration.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationShow(Long key) {
		return sourceConfiguration.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationCreateBulk() {
		return sourceConfiguration.createBulk();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationNewBulkForm() {
		return sourceConfiguration.newBulkForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationEditBulkForm(Long key) {
		return sourceConfiguration.editBulkForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationUpdateBulk(Long key) {
		return sourceConfiguration.updateBulk(key);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userApprove(String key, int page) {
		return user.approve(key, page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userSuspend(String key, int page) {
		return user.suspend(key, page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userList(String status, int page) {
		return user.list(status, page);
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

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapList() {
		return productScrap.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapNewForm() {
		return productScrap.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapCreate() {
		return productScrap.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapEditForm(Long key) {
		return productScrap.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapUpdate(Long key) {
		return productScrap.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapDelete(Long key) {
		return productScrap.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productScrapShow(Long key) {
		return productScrap.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceList() {
		return linkSource.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceCreate() {
		return linkSource.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceNewForm() {
		return linkSource.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceEditForm(Long key) {
		return linkSource.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceUpdate(Long key) {
		return linkSource.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceDelete(Long key) {
		return linkSource.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result linkSourceShow(Long key) {
		return linkSource.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileList() {
		return s3File.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileCreate() {
		return s3File.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileNewForm() {
		return s3File.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileEditForm(String key) {
		return s3File.editForm(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileUpdate(String key) {
		return s3File.update(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileDelete(String key) {
		return s3File.delete(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileShow(String key) {
		return s3File.show(UUID.fromString(key));
	}
	
	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordList() {
		return productRecord.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordNewForm() {
		return productRecord.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordCreate() {
		return productRecord.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordEditForm(Long key) {
		return productRecord.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordUpdate(Long key) {
		return productRecord.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordDelete(Long key) {
		return productRecord.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result productRecordShow(Long key) {
		return productRecord.show(key);
	}

	 */
}