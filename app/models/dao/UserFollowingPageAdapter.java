package models.dao;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Page;

import models.*;

public class UserFollowingPageAdapter extends UserFollowerPageAdapter {

	public UserFollowingPageAdapter(Page<UserFollow> userFollowPage, UserDAO userDAO) {
		super(userFollowPage, userDAO);
	}

	@Override
	public List<User> getList() {
		List<UserFollow> followList = delegate.getList();
		List<User> list = new ArrayList<User>();
		for (UserFollow follow : followList) {
			String sourceKey = follow.getKey().getTargetKey();
			User user = userDAO.get(sourceKey);
			if (user != null)
				list.add(user);
			
		}
		return list;
	}

}
