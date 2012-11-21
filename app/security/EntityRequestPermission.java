package security;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Logger;
import play.Logger.ALogger;
import play.mvc.Http.Request;
import be.objectify.deadbolt.models.Permission;

public class EntityRequestPermission implements RequestPermission {

	private static ALogger log = Logger.of(EntityRequestPermission.class);
	
	private final Pattern[] findPatterns;
	private final Class<?> entity;
	private final String action;

	public EntityRequestPermission(Class<?> entity,
			String action, Pattern... findPatterns) {
		this.findPatterns = findPatterns;
		this.entity = entity;
		this.action = action;
	}

	public boolean isAllowed(Request request,
			List<? extends Permission> permissions) {
		if (log.isDebugEnabled())
			log.debug("isAllowed() <-");
		
		String requires = requiresPermission(request);
		if (log.isDebugEnabled())
			log.debug("requires : " + requires);
		
		if (requires != null) {
			for (Permission permission : permissions) {
				if (log.isDebugEnabled())
					log.debug("permission : " + permission);
				
				String value = permission.getValue();
				if (value.equals(requires))
					return true;
			}
		}
		return false;
	}

	protected String requiresPermission(Request request) {
		String path = request.path();
		for (Pattern findPattern : findPatterns) {
			Matcher m = findPattern.matcher(path);
			if (m.find()) {
			    String val = m.group(1);
			    return new EntityPermission(entity, action, val).getValue();
			}
		}
		return null;
	}
}
