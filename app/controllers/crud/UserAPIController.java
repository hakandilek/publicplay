package controllers.crud;

import static play.libs.Json.toJson;

import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;

import models.User;
import models.UserFollow;
import models.UserFollowPK;
import models.dao.UserDAO;
import models.dao.UserFollowDAO;
import play.mvc.Result;
import play.utils.crud.APIController;
import play.utils.dao.EntityNotFoundException;

import com.google.common.collect.ImmutableMap;

import controllers.HttpUtils;

public class UserAPIController extends APIController<String, User> {

	private UserFollowDAO userFollowDAO;

	@Inject
	public UserAPIController(UserDAO userDAO, UserFollowDAO userFollowDAO)  {
		super(userDAO);
		this.userFollowDAO = userFollowDAO;
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
		
		User m = new User();
		m.setStatus(User.Status.NEW);
		m.setUrl(url);
		Long key = dao.create(m);
		*/
		return TODO;
	}

	public Result bulkList() {
		if (log.isDebugEnabled())
			log.debug("bulkList() <-");
		
		//clean cyclic dependencies
		List<User> list = dao.all();
		for (User user : list) {
			user.getPosts().clear();
			user.getComments().clear();
		}
		
		return ok(toJson(list));
	}

	public Result follow(String key) {
		if (log.isDebugEnabled())
			log.debug("follow() <- " + key);
		String sourceKey = HttpUtils.loginUser().getKey();
		UserFollowPK followKey = new UserFollowPK(sourceKey, key);
		UserFollow follow = userFollowDAO.get(followKey);
		if (follow == null) {
			follow = new UserFollow();
			follow.setKey(followKey);
			userFollowDAO.create(follow);
		}

		return created(toJson(ImmutableMap.of("status", "OK", "key", key,
				"data", follow)));
	}

	public Result unfollow(String key) {
		if (log.isDebugEnabled())
			log.debug("follow() <- " + key);
		String sourceKey = HttpUtils.loginUser().getKey();
		UserFollowPK followKey = new UserFollowPK(sourceKey, key);
		UserFollow follow = userFollowDAO.get(followKey);
		if (follow != null) {
			try {
				userFollowDAO.remove(followKey);
			} catch (EntityNotFoundException e) {
				return ok(toJson(ImmutableMap.of("status", "not found", "key", key,
						"data", follow)));
			}
		}
		
		return created(toJson(ImmutableMap.of("status", "OK", "key", key,
				"data", follow)));
	}

	public Result roleUpdate(String key) {
		if (log.isDebugEnabled())
			log.debug("roleUpdate <- " + key);

		//field name & value
		JsonNode json = request().body().asJson();
		JsonNode node = json.get("name");
		if (node == null) return null;
		String name = node.getTextValue();
		
		String value = jsonText("value");
		if (log.isDebugEnabled())
			log.debug("name : " + name);
		if (log.isDebugEnabled())
			log.debug("value : " + value);
		System.out.println(name);
		System.out.println(value);

		User u = dao.get(key);
		if (log.isDebugEnabled())
			log.debug("m : " + u);
		if (u == null) {
			return notFound(toJson(ImmutableMap.of(
					"status", "OK",
					"key", key,
					"message", "entity with the given key not found")));
		}

		String fieldName = Character.toUpperCase(name.charAt(0)) + name.substring(1);

		dao.update(key, u);
		if (log.isDebugEnabled())
			log.debug("updated.");
		if (log.isDebugEnabled())
			log.debug("m : " + u);

		return ok(toJson(ImmutableMap.of(
				"status", "OK",
				"key", key,
				"message", "field[" + name +
				"] updated")));
	}



}
