package controllers.crud;

import java.util.UUID;

import javax.inject.Inject;

import models.S3File;
import models.dao.S3FileDAO;
import play.mvc.Result;
import play.utils.crud.APIController;


public class S3FileAPIController extends APIController<UUID, S3File> {

	@Inject
	public S3FileAPIController(S3FileDAO s3FileDAO) {
		super(s3FileDAO);
	}

	@Override
	public Result create() {
		/* TODO:
		Result check = checkRequired("url");
		if (check != null) {
			return check;
		}

		String url = jsonText("url");
		try {
			new URL(url );
		} catch (MalformedURLException e) {
			return badRequest(toJson(ImmutableMap.of(
					"status", "error", 
					"message", e.getMessage())));
		}
		
		S3File m = new S3File();
		m.setStatus(S3File.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}


}
