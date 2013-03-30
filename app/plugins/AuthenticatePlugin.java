package plugins;

import java.util.Date;

import models.User;
import models.dao.UserDAO;
import play.Application;
import play.Logger;
import play.Logger.ALogger;

import com.feth.play.module.pa.providers.oauth2.OAuth2AuthInfo;
import com.feth.play.module.pa.providers.oauth2.facebook.FacebookAuthUser;
import com.feth.play.module.pa.service.UserServicePlugin;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;

public class AuthenticatePlugin extends UserServicePlugin {
	
	private static ALogger log = Logger.of(AuthenticatePlugin.class);

	private static AuthenticatePlugin instance;// plugin instance

	private UserDAO userDAO;

	public AuthenticatePlugin(Application app) {
		super(app);
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " created.");
	}

    @Override
    public Object save(final AuthUser authUser) {
    	if (log.isDebugEnabled())
			log.debug("save <-");
    	if (log.isDebugEnabled())
			log.debug("authUser : " + authUser);
    	String userKey = User.getKey(authUser.getProvider(), authUser.getId());
		User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		
        if (user == null) {
        	user = new User();
        	user.setKey(authUser.getProvider(), authUser.getId());
			if (authUser instanceof FacebookAuthUser) {
				FacebookAuthUser fbu = (FacebookAuthUser) authUser;
    			// Remember, even when getting them from FB & Co., emails should be
    			// verified within the application as a security breach there might
    			// break your security as well!
    			//TODO:user.setEmailValidated(false);
    			user.setEmail(fbu.getEmail());
				user.setFirstName(fbu.getFirstName());
				user.setLastName(fbu.getLastName());
				user.setGender(fbu.getGender());
				OAuth2AuthInfo authInfo = fbu.getOAuth2AuthInfo();
				user.setAccessToken(authInfo.getAccessToken());
				user.setAccessExpires(new Date(authInfo.getExpiration()));
				user.setProfileImageURL(fbu.getPicture());
			}
    		
        	userDAO.create(user);
            return userKey;
        } else {
            // we have this user already, so return null
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLogin(new Date());
			userDAO.update(userKey, user);
            return null;
        }
    }

	@Override
	public AuthUser update(final AuthUser authUser) {
		// User logged in again
    	if (log.isDebugEnabled())
			log.debug("authUser : " + authUser);
    	String userKey = User.getKey(authUser.getProvider(), authUser.getId());
		User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
        if (user != null) {
        	// bump last login date
			user.setLastLogin(new Date());
			// update info from facebook 
			if (authUser instanceof FacebookAuthUser) {
				FacebookAuthUser fbu = (FacebookAuthUser) authUser;
    			// Remember, even when getting them from FB & Co., emails should be
    			// verified within the application as a security breach there might
    			// break your security as well!
    			//TODO:user.setEmailValidated(false);
    			user.setEmail(fbu.getEmail());
				user.setFirstName(fbu.getFirstName());
				user.setLastName(fbu.getLastName());
				user.setGender(fbu.getGender());
				OAuth2AuthInfo authInfo = fbu.getOAuth2AuthInfo();
				user.setAccessToken(authInfo.getAccessToken());
				user.setAccessExpires(new Date(authInfo.getExpiration()));
				user.setProfileImageURL(fbu.getPicture());
			}
			userDAO.update(userKey, user);
        }
    	return super.update(authUser);
	}
	
    @Override
    public Object getLocalIdentity(final AuthUserIdentity identity) {
    	if (log.isDebugEnabled())
			log.debug("getLocalIdentity <-");
    	String userKey = User.getKey(identity.getProvider(), identity.getId());
		User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
        if(user != null) {
            return user.getKey();
        } else {
            return null;
        }
    }

    @Override
    public AuthUser merge(final AuthUser newUser, final AuthUser oldUser) {
    	if (log.isDebugEnabled())
			log.debug("merge <-");
        if (!oldUser.equals(newUser)) {
            //TODO: User.merge(oldUser, newUser);
        }
        return oldUser;
    }

    @Override
    public AuthUser link(final AuthUser oldUser, final AuthUser newUser) {
    	if (log.isDebugEnabled())
			log.debug("link <-");
        //TODO: User.addLinkedAccount(oldUser, newUser);
        return null;
    }
    
    
	public User find(String userKey) {
		if (log.isDebugEnabled())
			log.debug("find() <-");
		if (log.isDebugEnabled())
			log.debug("userKey : " + userKey);
		
		final User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}
	
	public User find(AuthUser authUser) {
		if (log.isDebugEnabled())
			log.debug("find <- " + authUser);
		
		if (authUser == null) return null;
    	String userKey = User.getKey(authUser.getProvider(), authUser.getId());
		User user = userDAO.get(userKey);
		if (log.isDebugEnabled())
			log.debug("user : " + user);
		return user;
	}

	@Override
	public void onStart() {
		instance = this;
		userDAO = GuicePlugin.getInstance().getInstance(UserDAO.class);
		super.onStart();
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " started.");

	}

	@Override
	public void onStop() {
		instance = null;
		userDAO = null;
		super.onStop();
		if (log.isInfoEnabled())
			log.debug(getClass().getSimpleName() + " stopped.");
	}

	public static AuthenticatePlugin getInstance() {
		return instance;
	}

}
