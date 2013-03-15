package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.ReputationValue;
import models.dao.ReputationValueDAO;
import play.mvc.Call;
import play.utils.crud.CRUDController;
import controllers.routes;

public class ReputationValueCRUDController extends CRUDController<String, ReputationValue> {

	@Inject
	public ReputationValueCRUDController(ReputationValueDAO dao) {
		super(dao, form(ReputationValue.class), String.class, ReputationValue.class, 20, "name");
	}

	@Override
	protected String templateForForm() {
		return "admin.reputationValueForm";
	}

	@Override
	protected String templateForList() {
		return "admin.reputationValueList";
	}

	@Override
	protected String templateForShow() {
		return "admin.reputationValueShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.reputationValueList(0);
	}

}
