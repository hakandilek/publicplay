package controllers;

import models.S3File;
import models.SecurityRole;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.RawBuffer;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import plugins.AuthenticatePlugin;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;

public class HttpUtils {
	
	private static ALogger log = Logger.of(HttpUtils.class);

	private static AuthenticatePlugin userService = AuthenticatePlugin.getInstance();
	
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
		SecurityRole admin = new SecurityRole(-1L, "admin");
		return user.getRoles().contains(admin);
	}

	public static S3File uploadFileMultipart(Request r, String field, String parent) {
		if (log.isDebugEnabled())
			log.debug("uploadFileMultipart <-");
		RequestBody b = r.body();
		MultipartFormData body = b.asMultipartFormData();
		if (log.isDebugEnabled())
			log.debug("body : " + body);

		FilePart filePart = body.getFile(field);
		if (log.isDebugEnabled())
			log.debug("filePart : " + filePart);

		S3File file = new S3File();
		file.parent = parent;
		if (filePart != null) {
			file.name = filePart.getFilename();
			file.setInputFromFile(filePart.getFile());
		}
		
		if (log.isDebugEnabled())
			log.debug("file : " + file);
		return file;
	}

	public static S3File uploadFile(Request r, String paramFilename, String parent) {
		if (log.isDebugEnabled())
			log.debug("uploadFile <-");
		String filename = r.getQueryString("qqfile");
		if (log.isDebugEnabled())
			log.debug("filename : " + filename);

		RequestBody b = r.body();
		RawBuffer rb = b.asRaw();
		byte[] data = rb.asBytes();
		S3File f = new S3File();
		f.parent = parent;
		f.setInputFromData(data);
		f.name = filename;
		
		if (log.isDebugEnabled())
			log.debug("f : " + f);
		return f;
	}

}
