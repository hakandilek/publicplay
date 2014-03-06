package forms;

import static play.libs.Json.*;

import java.util.ArrayList;
import java.util.List;

import models.User;
import models.User.Status;

import com.fasterxml.jackson.databind.JsonNode;

import play.Logger;
import play.Logger.ALogger;
import play.data.validation.Constraints.Required;

public class BulkUser {

	private static ALogger log = Logger.of(BulkUser.class);

	@Required
	private String bulkData;

	public BulkUser() {
		super();
	}

	public List<User> toModel() {
		if (log.isDebugEnabled())
			log.debug("toModel() <-");

		JsonNode jsnode = parse(bulkData);
		if (log.isDebugEnabled())
			log.debug("jsnode : " + jsnode);
		
		List<User> list = new ArrayList<User>();
		@SuppressWarnings("unchecked")
		List<Object> objects = fromJson(jsnode, List.class);
		for (Object o: objects) {
			JsonNode json = toJson(o);
			User user = fromJson(json, User.class);
			user.setStatus(Status.NEW);
			list.add(user);
		}
		if (log.isDebugEnabled())
			log.debug("list : " + list);
		
		return list;
	}

	public String getBulkData() {
		return bulkData;
	}

	public void setBulkData(String bulkData) {
		this.bulkData = bulkData;
	}

}
