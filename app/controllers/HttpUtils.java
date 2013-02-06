package controllers;

import javax.inject.Inject;

import models.S3File;
import models.User;
import models.dao.UserDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Context;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import socialauth.core.SocialUser;
import controllers.crud.SocialController;

public class HttpUtils {

	private static ALogger log = Logger.of(HttpUtils.class);
	
	UserDAO userDAO;
	
	@Inject 
	public HttpUtils(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public String selfURL() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * extract login user from the context if available
	 * @param ctx http context
	 * @return login user, or null
	 */
	public User loginUser(Context ctx) {
		final SocialUser su = (SocialUser) ctx.args.get(SocialController.USER_KEY);
		if (log.isDebugEnabled())
			log.debug("su : " + (su == null ? null : su.getUserKey()));
		User user = null;
		if (su != null) {
			user = userDAO.get(su.getUserKey());
			if (user == null) {
				if (log.isDebugEnabled())
					log.debug("user not found in DB, creating from session");
				user  = new User(su);
			}
		}
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
