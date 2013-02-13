package controllers;

import models.S3File;
import models.User;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;

public class HttpUtils {

	private static ALogger log = Logger.of(HttpUtils.class);
	
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
	public User loginUser() {
		Context ctx = Http.Context.current();
		return loginUser(ctx);
	}
	
	/**
	 * extract login user from the context if available
	 * @param ctx http context
	 * @return login user, or null
	 */
	public User loginUser(Context ctx) {
		User user = (User) ctx.args.get(SocialController.USER);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}
	
	public S3File uploadFile(Request r, String field) {
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
