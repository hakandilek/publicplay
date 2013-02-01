package controllers;

import java.util.List;
import java.util.UUID;

import models.CRUDModel;
import models.Comment;
import models.Post;
import models.S3File;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;
import security.RestrictApproved;
import socialauth.core.Secure;
import utils.HttpUtils;
import admin.CommentAdminPage;
import admin.PostAdminPage;
import admin.S3FileAdminPage;
import admin.UserAdminPage;
import be.objectify.deadbolt.actions.Restrict;

import com.avaje.ebean.Page;

import crud.controllers.CRUD;
import crud.controllers.CRUDController;
import crud.controllers.CachedCRUD;
import crud.controllers.MetaPage;

public class AdminController extends CRUDController {

	public static final int PAGE_SIZE = CRUD.PAGE_SIZE;

	private static ALogger log = Logger.of(AdminController.class);
	
	private final static AdminController instance = new AdminController();

	public AdminController() {
		super(new CRUD[] { 
				new CachedCRUD<String, User>(User.class, User.find, new UserAdminPage()),
				new CachedCRUD<Long, Post>(Post.class, Post.find, new PostAdminPage()),
				new CachedCRUD<Long, Comment>(Comment.class, Comment.find, new CommentAdminPage()),
				new CachedCRUD<UUID, S3File>(S3File.class, S3File.find, new S3FileAdminPage()),
		});
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result index() {
		return instance.doIndex();
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result list(String model, int page) {
		return instance.doList(model, page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result newForm(String model) {
		return instance.doNewForm(model);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result create(String model) {
		return instance.doCreate(model);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result editForm(String model, String key) {
		return instance.doEditForm(model, key);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result update(String model, String key) {
		return instance.doUpdate(model, key);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result delete(String model, String key) {
		return instance.doDelete(model, key);
	}
	
	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result show(String model, String key) {
		return instance.doShow(model, key);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userApprove(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userApprove() <-");
		
		User user = User.get(key);
		user.setStatus(User.Status.APPROVED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		User.update(user);
		return list("User", page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userSuspend(String key, int page) {
		if (log.isDebugEnabled())
			log.debug("userSuspend() <-");
		
		User user = User.get(key);
		user.setStatus(User.Status.SUSPENDED);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
		User.update(user);
		return list("User", page);
	}

	@Secure
	@Restrict("admin")
	@RestrictApproved
	public static Result userList(String status, int page) {
		User user = HttpUtils.loginUser(ctx());
		models.User.Status userStatus = models.User.Status.valueOf(status);
		if (userStatus == null) userStatus = models.User.Status.NEW;
		if (log.isDebugEnabled())
			log.debug("userStatus : " + userStatus);
		
		String keyFieldName = "key";
		String[] strings = new String[]{"key", "profile", "lastLogin", "loginCount"};
		@SuppressWarnings("unchecked")
		List<String> fieldNames = Arrays.asList(strings);
		Page<User> pg = User.page(page, PAGE_SIZE, userStatus);
		Page<CRUDModel> metaPage = new MetaPage<User>(pg, keyFieldName,
				fieldNames);
		return ok(views.html.admin.userList.render(metaPage, "User",
				keyFieldName, fieldNames, user, userStatus));
	}

}
