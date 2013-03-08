package controllers;


import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import models.UserFollowPK;
import play.mvc.Controller;
import play.mvc.Result;
import security.RestrictApproved;
import socialauth.core.Secure;
import be.objectify.deadbolt.actions.Restrict;
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

	public static final int PAGE_SIZE = 20;

	@Inject static CategoryCRUDController categoryController;
	@Inject static ReputationValueCRUDController reputationValueController;
	@Inject static CommentCRUDController commentController;
	@Inject static PostCRUDController postController;
	@Inject static PostRatingCRUDController postRatingController;
	@Inject static S3FileCRUDController s3FileController;
	@Inject static SecurityRoleCRUDController securityRoleController;
	@Inject static UserCRUDController userController;
	@Inject static UserAPIController userAPI;
	@Inject static SourceConfigurationCRUDController sourceConfigurationController;
	@Inject static UserFollowCRUDController userFollowController;
	@Inject static ContentReportCRUDController contentReportController;

	@Secure @Restrict("admin") @RestrictApproved public static Result index() {
		return ok(views.html.admin.index.render());
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryList(int page) {
		return categoryController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryNewForm() {
		return categoryController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryCreate() {
		return categoryController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryEditForm(String key) {
		return categoryController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryUpdate(String key) {
		return categoryController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryDelete(String key) {
		return categoryController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result categoryShow(String key) {
		return categoryController.show(key);
	}
	
	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueList(int page) {
		return reputationValueController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueNewForm() {
		return reputationValueController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueCreate() {
		return reputationValueController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueEditForm(String key) {
		return reputationValueController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueUpdate(String key) {
		return reputationValueController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueDelete(String key) {
		return reputationValueController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result reputationValueShow(String key) {
		return reputationValueController.show(key);
	}
	
	@Secure @Restrict("admin") @RestrictApproved public static Result commentList(int page) {
		return commentController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentNewForm() {
		return commentController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentCreate() {
		return commentController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentEditForm(Long key) {
		return commentController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentUpdate(Long key) {
		return commentController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentDelete(Long key) {
		return commentController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result commentShow(Long key) {
		return commentController.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postList(int page) {
		return postController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postNewForm() {
		return postController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postCreate() {
		return postController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postUpdate(Long key) {
		return postController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postShow(Long key) {
		return postController.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingList(int page) {
		return postRatingController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingNewForm() {
		return postRatingController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingCreate() {
		return postRatingController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingEditForm(String key) {
		return postRatingController.editForm(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingUpdate(String key) {
		return postRatingController.update(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingDelete(String key) {
		return postRatingController.delete(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result postRatingShow(String key) {
		return postRatingController.show(PostRatingPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileList(int page) {
		return s3FileController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileNewForm() {
		return s3FileController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileCreate() {
		return s3FileController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileEditForm(String key) {
		return s3FileController.editForm(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileUpdate(String key) {
		return s3FileController.update(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileDelete(String key) {
		return s3FileController.delete(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result s3FileShow(String key) {
		return s3FileController.show(UUID.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleList(int page) {
		return securityRoleController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleNewForm() {
		return securityRoleController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleCreate() {
		return securityRoleController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleEditForm(Long key) {
		return securityRoleController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleUpdate(Long key) {
		return securityRoleController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleDelete(Long key) {
		return securityRoleController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result securityRoleShow(Long key) {
		return securityRoleController.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userList(int page) {
		return userController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userNewForm() {
		return userController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userCreate() {
		return userController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userEditForm(String key) {
		return userController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userUpdate(String key) {
		return userController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userDelete(String key) {
		return userController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userShow(String key) {
		return userController.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationList(int page) {
		return sourceConfigurationController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationCreate() {
		return sourceConfigurationController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationNewForm() {
		return sourceConfigurationController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationEditForm(Long key) {
		return sourceConfigurationController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationUpdate(Long key) {
		return sourceConfigurationController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationDelete(Long key) {
		return sourceConfigurationController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationShow(Long key) {
		return sourceConfigurationController.show(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationCreateBulk() {
		return sourceConfigurationController.createBulk();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationNewBulkForm() {
		return sourceConfigurationController.newBulkForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationEditBulkForm(Long key) {
		return sourceConfigurationController.editBulkForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result sourceConfigurationUpdateBulk(Long key) {
		return sourceConfigurationController.updateBulk(key);
	}
	
	@Secure @Restrict("admin") @RestrictApproved public static Result userCreateBulk() {
		return userController.createBulk();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userNewBulkForm() {
		return userController.newBulkForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userBulkList() {
		return userAPI.bulkList();
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userApprove(String key, int page) {
		return userController.approve(key, page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userSuspend(String key, int page) {
		return userController.suspend(key, page);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userRecalculateReputation(String key, int page) {
		return userController.recalculateReputation(key, page);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result calculateAllReputations() {
		return userController.calculateAllReputations();
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userList(String status, int page) {
		return userController.list(status, page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowList(int page) {
		return userFollowController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowNewForm() {
		return userFollowController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowCreate() {
		return userFollowController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowEditForm(String key) {
		return userFollowController.editForm(UserFollowPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowUpdate(String key) {
		return userFollowController.update(UserFollowPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowDelete(String key) {
		return userFollowController.delete(UserFollowPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result userFollowShow(String key) {
		return userFollowController.show(UserFollowPK.fromString(key));
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportList(int page) {
		return contentReportController.list(page);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportNewForm() {
		return contentReportController.newForm();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportCreate() {
		return contentReportController.create();
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportEditForm(Long key) {
		return contentReportController.editForm(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportUpdate(Long key) {
		return contentReportController.update(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportDelete(Long key) {
		return contentReportController.delete(key);
	}

	@Secure @Restrict("admin") @RestrictApproved public static Result contentReportShow(Long key) {
		return contentReportController.show(key);
	}

}