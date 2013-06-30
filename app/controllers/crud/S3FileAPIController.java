package controllers.crud;

import static play.libs.Json.toJson;

import java.util.UUID;

import javax.inject.Inject;

import models.S3File;
import models.dao.S3FileDAO;
import play.Logger;
import play.Logger.ALogger;
import play.mvc.Result;
import play.utils.crud.APIController;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;


public class S3FileAPIController extends APIController<UUID, S3File> {

	private static ALogger log = Logger.of(S3FileAPIController.class);
	
	@Inject
	public S3FileAPIController(S3FileDAO s3FileDAO) {
		super(s3FileDAO);
	}

	@Override
	public Result create() {
		if (log.isDebugEnabled())
			log.debug("create <-");
		
		S3File file = null;
		UUID key = null;
		try {
			file = HttpUtils.uploadFile(request(), "qqfile", "Post");
			if (log.isDebugEnabled())
				log.debug("file : " + file);

			key = dao.create(file);
		} catch (Exception e) {
			log.error("exception occured : " + e, e);
		}
		
		if (file != null && key != null) {
			return ok(toJson(ImmutableMap.of("status", "OK", "success", "true",
					"key", key, "data", file)));
		} else {
			return internalServerError(toJson(ImmutableMap.of("status", "error")));
		}
	}


}
