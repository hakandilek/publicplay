package controllers;

import javax.inject.Inject;

import models.Action;
import models.ActionSubject;
import models.ActionType;
import models.User;
import models.dao.ActionDAO;
import play.Logger;
import play.Logger.ALogger;

public class ActionHandler {

	private static ALogger log = Logger.of(ActionHandler.class);

	ActionDAO actionDAO;

	@Inject
	public ActionHandler(ActionDAO actionDAO) {
		super();
		this.actionDAO = actionDAO;
	}

	public void perform(User user, ActionSubject subject, ActionType actionType) {
		if (log.isDebugEnabled())
			log.debug("perform <-");
		if (log.isDebugEnabled())
			log.debug("actionType : " + actionType);
		if (log.isDebugEnabled())
			log.debug("subject : " + subject);
		if (log.isDebugEnabled())
			log.debug("user : " + user);

		Action action = new Action();
		action.setCreatedBy(user);
		action.setType(actionType);
		subject.associate(action);
		actionDAO.create(action);
	}


}
