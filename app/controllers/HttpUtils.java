package controllers;

import models.S3File;
import models.SecurityRole;
import models.User;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import plugins.AuthenticatePlugin;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

public class HttpUtils {

	private static AuthenticatePlugin userService = AuthenticatePlugin.getInstance();
	
	public HttpUtils() {
		super();
	}

	public String selfURL() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * extract login user from the context if available
	 * @return login user, or null
	 */
	public static User loginUser() {
		Context ctx = Http.Context.current();
		return loginUser(ctx);
	}
	
	/**
	 * extract login user from the context if available
	 * @param ctx http context
	 * @return login user, or null
	 */
	public static User loginUser(Context ctx) {
		AuthUser authUser = PlayAuthenticate.getUser(ctx);
		if (authUser == null) return null;
		return userService.find(authUser);
	}
	
	public static boolean isAdmin() {
		User user = loginUser();
		return isAdmin(user);
	}
	
	public static boolean isAdmin(User user) {
		if (user == null) return false;
		SecurityRole admin = new SecurityRole("admin");
		return user.getRoles().contains(admin);
	}

	public static S3File uploadFile(Request r, String field) {
		RequestBody b = r.body();
		MultipartFormData body = b.asMultipartFormData();
		FilePart filePart = body.getFile(field);
		if (filePart != null) {
			S3File file = new S3File();
			file.name = filePart.getFilename();
			file.file = filePart.getFile();
			return file;
		}
		return null;
	}

}
