package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import play.Application;
import scala.Option;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.SocialUser;
import securesocial.core.java.UserId;

/**
 * A Sample In Memory user service in Java
 */
public class UserService extends BaseUserService {
    private HashMap<String, SocialUser> users  = new HashMap<String,SocialUser>();

    public UserService(Application application) {
        super(application);
    }

    @Override
    public void doSave(SocialUser user) {
    	System.out.println("user : " + user);
        users.put(user.id.id + user.id.provider, user);
    }

    @Override
    public SocialUser doFind(UserId userId) {
        return users.get(userId.id + userId.provider);
    }    
    
}
