package controllers;


import java.util.UUID;

import javax.inject.Inject;

import models.PostRatingPK;
import models.UserFollowPK;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authenticated;
import security.RestrictApproved;
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

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result index() {
		return ok(views.html.admin.index.render());
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryList(int page) {
		return categoryController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryNewForm() {
		return categoryController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryCreate() {
		return categoryController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryEditForm(String key) {
		return categoryController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryUpdate(String key) {
		return categoryController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryDelete(String key) {
		return categoryController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result categoryShow(String key) {
		return categoryController.show(key);
	}
	
	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueList(int page) {
		return reputationValueController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueNewForm() {
		return reputationValueController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueCreate() {
		return reputationValueController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueEditForm(String key) {
		return reputationValueController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueUpdate(String key) {
		return reputationValueController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueDelete(String key) {
		return reputationValueController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result reputationValueShow(String key) {
		return reputationValueController.show(key);
	}
	
	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentList(int page) {
		return commentController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentNewForm() {
		return commentController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentCreate() {
		return commentController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentEditForm(Long key) {
		return commentController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentUpdate(Long key) {
		return commentController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentDelete(Long key) {
		return commentController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result commentShow(Long key) {
		return commentController.show(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postList(int page) {
		return postController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postNewForm() {
		return postController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postCreate() {
		return postController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postEditForm(Long key) {
		return postController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postUpdate(Long key) {
		return postController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postDelete(Long key) {
		return postController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postShow(Long key) {
		return postController.show(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingList(int page) {
		return postRatingController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingNewForm() {
		return postRatingController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingCreate() {
		return postRatingController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingEditForm(String key) {
		return postRatingController.editForm(PostRatingPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingUpdate(String key) {
		return postRatingController.update(PostRatingPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingDelete(String key) {
		return postRatingController.delete(PostRatingPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result postRatingShow(String key) {
		return postRatingController.show(PostRatingPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileList(int page) {
		return s3FileController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileNewForm() {
		return s3FileController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileCreate() {
		return s3FileController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileEditForm(String key) {
		return s3FileController.editForm(UUID.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileUpdate(String key) {
		return s3FileController.update(UUID.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileDelete(String key) {
		return s3FileController.delete(UUID.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result s3FileShow(String key) {
		return s3FileController.show(UUID.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleList(int page) {
		return securityRoleController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleNewForm() {
		return securityRoleController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleCreate() {
		return securityRoleController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleEditForm(Long key) {
		return securityRoleController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleUpdate(Long key) {
		return securityRoleController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleDelete(Long key) {
		return securityRoleController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result securityRoleShow(Long key) {
		return securityRoleController.show(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userList(int page) {
		return userController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userNewForm() {
		return userController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userCreate() {
		return userController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userEditForm(String key) {
		return userController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userUpdate(String key) {
		return userController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userDelete(String key) {
		return userController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userShow(String key) {
		return userController.show(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public F.Promise<Result> userReload(String key) {
		return userController.reload(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationList(int page) {
		return sourceConfigurationController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationCreate() {
		return sourceConfigurationController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationNewForm() {
		return sourceConfigurationController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationEditForm(Long key) {
		return sourceConfigurationController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationUpdate(Long key) {
		return sourceConfigurationController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationDelete(Long key) {
		return sourceConfigurationController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationShow(Long key) {
		return sourceConfigurationController.show(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationCreateBulk() {
		return sourceConfigurationController.createBulk();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationNewBulkForm() {
		return sourceConfigurationController.newBulkForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationEditBulkForm(Long key) {
		return sourceConfigurationController.editBulkForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result sourceConfigurationUpdateBulk(Long key) {
		return sourceConfigurationController.updateBulk(key);
	}
	
	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userCreateBulk() {
		return userController.createBulk();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userNewBulkForm() {
		return userController.newBulkForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userBulkList() {
		return userAPI.bulkList();
	}

	@Authenticated
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userApprove(String key, int page) {
		return userController.approve(key, page);
	}

	@Authenticated
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userSuspend(String key, int page) {
		return userController.suspend(key, page);
	}
	
	@Authenticated
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userRecalculateReputation(String key, int page) {
		return userController.recalculateReputation(key, page);
	}
	
	@Authenticated
	@Restrict(@Group("admin"))
	@RestrictApproved
	public F.Promise<Result> calculateAllReputations() {
		return userController.calculateAllReputations();
	}

	@Authenticated
	@Restrict(@Group("admin"))
	@RestrictApproved
	public Result userList(String status, int page) {
		return userController.list(status, page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowList(int page) {
		return userFollowController.list(page);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowNewForm() {
		return userFollowController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowCreate() {
		return userFollowController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowEditForm(String key) {
		return userFollowController.editForm(UserFollowPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowUpdate(String key) {
		return userFollowController.update(UserFollowPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowDelete(String key) {
		return userFollowController.delete(UserFollowPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result userFollowShow(String key) {
		return userFollowController.show(UserFollowPK.fromString(key));
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportList() {
		return contentReportController.list(null, 0);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportNewForm() {
		return contentReportController.newForm();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportCreate() {
		return contentReportController.create();
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportEditForm(Long key) {
		return contentReportController.editForm(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportUpdate(Long key) {
		return contentReportController.update(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportDelete(Long key) {
		return contentReportController.delete(key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportShow(String contentType, Long contentKey, Long key) {
		return contentReportController.show(contentType, contentKey, key);
	}

	@Authenticated @Restrict(@Group("admin")) @RestrictApproved public Result contentReportList(String status, int page) {
		return contentReportController.list(status, page);
	}
}