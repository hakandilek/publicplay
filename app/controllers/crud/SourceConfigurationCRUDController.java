package controllers.crud;
import static play.data.Form.*;

import javax.inject.Inject;

import models.SourceConfiguration;
import models.dao.SourceConfigurationDAO;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import controllers.routes;
import forms.BulkConfiguration;

public class SourceConfigurationCRUDController extends
		CRUDController<Long, SourceConfiguration> {

	Form<BulkConfiguration> bulkForm = form(BulkConfiguration.class);
	private SourceConfigurationDAO sourceConfigurationDAO;
	
	@Inject
	public SourceConfigurationCRUDController(SourceConfigurationDAO dao) {
		super(dao, form(SourceConfiguration.class), Long.class,
				SourceConfiguration.class, 20, "sourceKey");
		sourceConfigurationDAO = dao;
	}

	@Override
	protected String templateForList() {
		return "admin.configList";
	}

	@Override
	protected String templateForForm() {
		return "admin.configForm";
	}

	@Override
	protected String templateForShow() {
		return "admin.configShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.sourceConfigurationList(0);
	}

	protected String templateForBulkForm() {
		return "admin.configBulkForm";
	}

	public Result newBulkForm() {
		if (log.isDebugEnabled())
			log.debug("newBulkForm() <-");

		return ok(templateForBulkForm(),
				with(Long.class, null).and(Form.class, bulkForm));
	}

	public Result createBulk() {
		if (log.isDebugEnabled())
			log.debug("createBulk() <-");

		Form<BulkConfiguration> filledForm = bulkForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForBulkForm(),
					with(Long.class, null).and(Form.class, filledForm));
		} else {
			BulkConfiguration formModel = filledForm.get();
			SourceConfiguration model = formModel.toModel();
			sourceConfigurationDAO.create(model);
			if (log.isDebugEnabled())
				log.debug("entity created");

			return redirect(toIndex());
		}
	}

	public Result editBulkForm(Long key) {
		if (log.isDebugEnabled())
			log.debug("editBulkForm() <-" + key);

		SourceConfiguration model = sourceConfigurationDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("model : " + model);
		BulkConfiguration formModel = new BulkConfiguration(model);

		Form<BulkConfiguration> frm = bulkForm.fill(formModel);
		return ok(templateForBulkForm(), with(Long.class, key).and(Form.class, frm));
	}

	public Result updateBulk(Long key) {
		if (log.isDebugEnabled())
			log.debug("updateBulk() <-" + key);

		SourceConfiguration original = sourceConfigurationDAO.get(key);
		if (log.isDebugEnabled())
			log.debug("original : " + original);
		BulkConfiguration dbModel = new BulkConfiguration(original);
		Form<BulkConfiguration> filledForm = bulkForm.fill(dbModel).bindFromRequest();
		if (log.isDebugEnabled())
			log.debug("filledForm : " + filledForm);
		if (filledForm.hasErrors()) {
			if (log.isDebugEnabled())
				log.debug("validation errors occured");

			return badRequest(templateForBulkForm(),
					with(Long.class, key).and(Form.class, filledForm));
		} else {
			BulkConfiguration formModel = filledForm.get();
			if (log.isDebugEnabled())
				log.debug("formModel : " + formModel);
			SourceConfiguration model = formModel.toModel();
			if (log.isDebugEnabled())
				log.debug("model : " + model);
			sourceConfigurationDAO.update(model);
			if (log.isDebugEnabled())
				log.debug("entity updated");

			return redirect(toIndex());
		}
	}

}
