package controllers.crud;

import java.util.UUID;

import javax.inject.Inject;

import models.S3File;
import models.dao.S3FileDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class S3FileController extends CRUDController<UUID, S3File> {

	@Inject
	public S3FileController(S3FileDAO dao) {
		super(dao, form(S3File.class), UUID.class, S3File.class, 20, "updatedOn desc");
	}

	@Override
	protected String templateForForm() {
		return "admin.s3FileForm";
	}

	@Override
	protected String templateForList() {
		return "admin.s3FileList";
	}

	@Override
	protected String templateForShow() {
		return "admin.s3FileShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.s3FileList(0);
	}

}
