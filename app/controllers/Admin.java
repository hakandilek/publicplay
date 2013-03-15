package controllers;


import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import models.UserFollowPK;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import be.objectify.deadbolt.java.actions.*;
import controllers.crud.CategoryCRUDController;
import controllers.crud.CommentCRUDController;
import controllers.crud.ContentReportCRUDController;
import controllers.crud.PostCRUDController;
import controllers.crud.PostRatingCRUDController;
import controllers.crud.ReputationValueCRUDController;
import controllers.crud.S3FileCRUDController;
import controllers.crud.SecurityRoleCRUDController;
import controllers.crud.SourceConfigurationCRUDController;
import controllers.crud.UserAPIController;
import controllers.crud.UserCRUDController;
import controllers.crud.UserFollowCRUDController;

public class Admin extends Controller {

	@Inject CategoryCRUDController categoryController;
	@Inject ReputationValueCRUDController reputationValueController;
	@Inject CommentCRUDController commentController;
	@Inject PostCRUDController postController;
	@Inject PostRatingCRUDController postRatingController;
	@Inject S3FileCRUDController s3FileController;
	@Inject SecurityRoleCRUDController securityRoleController;
	@Inject UserCRUDController userController;
	@Inject UserAPIController userAPI;
	@Inject SourceConfigurationCRUDController sourceConfigurationController;
	@Inject UserFollowCRUDController userFollowController;
	@Inject ContentReportCRUDController contentReportController;

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result index() {
		return ok(views.html.admin.index.render());
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryList(int page) {
		return categoryController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryNewForm() {
		return categoryController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryCreate() {
		return categoryController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryEditForm(String key) {
		return categoryController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryUpdate(String key) {
		return categoryController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryDelete(String key) {
		return categoryController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result categoryShow(String key) {
		return categoryController.show(key);
	}
	
	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueList(int page) {
		return reputationValueController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueNewForm() {
		return reputationValueController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueCreate() {
		return reputationValueController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueEditForm(String key) {
		return reputationValueController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueUpdate(String key) {
		return reputationValueController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueDelete(String key) {
		return reputationValueController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueShow(String key) {
		return reputationValueController.show(key);
	}
	
	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentList(int page) {
		return commentController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentNewForm() {
		return commentController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentCreate() {
		return commentController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentEditForm(Long key) {
		return commentController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentUpdate(Long key) {
		return commentController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentDelete(Long key) {
		return commentController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result commentShow(Long key) {
		return commentController.show(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postList(int page) {
		return postController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postNewForm() {
		return postController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postCreate() {
		return postController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postUpdate(Long key) {
		return postController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postShow(Long key) {
		return postController.show(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingList(int page) {
		return postRatingController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingNewForm() {
		return postRatingController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingCreate() {
		return postRatingController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingEditForm(String key) {
		return postRatingController.editForm(PostRatingPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingUpdate(String key) {
		return postRatingController.update(PostRatingPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingDelete(String key) {
		return postRatingController.delete(PostRatingPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result postRatingShow(String key) {
		return postRatingController.show(PostRatingPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileList(int page) {
		return s3FileController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileNewForm() {
		return s3FileController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileCreate() {
		return s3FileController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileEditForm(String key) {
		return s3FileController.editForm(UUID.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileUpdate(String key) {
		return s3FileController.update(UUID.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileDelete(String key) {
		return s3FileController.delete(UUID.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result s3FileShow(String key) {
		return s3FileController.show(UUID.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleList(int page) {
		return securityRoleController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleNewForm() {
		return securityRoleController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleCreate() {
		return securityRoleController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleEditForm(Long key) {
		return securityRoleController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleUpdate(Long key) {
		return securityRoleController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleDelete(Long key) {
		return securityRoleController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleShow(Long key) {
		return securityRoleController.show(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userList(int page) {
		return userController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userNewForm() {
		return userController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userCreate() {
		return userController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userEditForm(String key) {
		return userController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userUpdate(String key) {
		return userController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userDelete(String key) {
		return userController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userShow(String key) {
		return userController.show(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationList(int page) {
		return sourceConfigurationController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationCreate() {
		return sourceConfigurationController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationNewForm() {
		return sourceConfigurationController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationEditForm(Long key) {
		return sourceConfigurationController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationUpdate(Long key) {
		return sourceConfigurationController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationDelete(Long key) {
		return sourceConfigurationController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationShow(Long key) {
		return sourceConfigurationController.show(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationCreateBulk() {
		return sourceConfigurationController.createBulk();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationNewBulkForm() {
		return sourceConfigurationController.newBulkForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationEditBulkForm(Long key) {
		return sourceConfigurationController.editBulkForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationUpdateBulk(Long key) {
		return sourceConfigurationController.updateBulk(key);
	}
	
	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userCreateBulk() {
		return userController.createBulk();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userNewBulkForm() {
		return userController.newBulkForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userBulkList() {
		return userAPI.bulkList();
	}

	@Secure
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userApprove(String key, int page) {
		return userController.approve(key, page);
	}

	@Secure
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userSuspend(String key, int page) {
		return userController.suspend(key, page);
	}
	
	@Secure
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userRecalculateReputation(String key, int page) {
		return userController.recalculateReputation(key, page);
	}
	
	@Secure
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result calculateAllReputations() {
		return userController.calculateAllReputations();
	}

	@Secure
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userList(String status, int page) {
		return userController.list(status, page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowList(int page) {
		return userFollowController.list(page);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowNewForm() {
		return userFollowController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowCreate() {
		return userFollowController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowEditForm(String key) {
		return userFollowController.editForm(UserFollowPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowUpdate(String key) {
		return userFollowController.update(UserFollowPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowDelete(String key) {
		return userFollowController.delete(UserFollowPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result userFollowShow(String key) {
		return userFollowController.show(UserFollowPK.fromString(key));
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportList() {
		return contentReportController.list(null, 0);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportNewForm() {
		return contentReportController.newForm();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportCreate() {
		return contentReportController.create();
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportEditForm(Long key) {
		return contentReportController.editForm(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportUpdate(Long key) {
		return contentReportController.update(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportDelete(Long key) {
		return contentReportController.delete(key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportShow(String contentType, Long contentKey, Long key) {
		return contentReportController.show(contentType, contentKey, key);
	}

	@Secure @Restrict(@Group("admin")) @RestrictApproved public Result contentReportList(String status, int page) {
		return contentReportController.list(status, page);
	}
}