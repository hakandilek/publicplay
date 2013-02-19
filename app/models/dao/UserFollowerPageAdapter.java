package models.dao;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Page;

import models.*;

public class UserFollowerPageAdapter extends PageAdapter<UserFollow, User> {

	protected UserDAO userDAO;

	public UserFollowerPageAdapter(Page<UserFollow> userFollowPage, UserDAO userDAO) {
		super(userFollowPage);
		this.userDAO = userDAO;
	}

	@Override
	public List<User> getList() {
		List<UserFollow> followList = delegate.getList();
		List<User> list = new ArrayList<User>();
		for (UserFollow follow : followList) {
			String sourceKey = follow.getKey().getSourceKey();
			User user = userDAO.get(sourceKey);
			if (user != null)
				list.add(user);
			
		}
		return list;
	}

	@Override
	public PageAdapter<UserFollow, User> create(Page<UserFollow> delegate) {
		return new UserFollowerPageAdapter(delegate, userDAO);
	}

}
