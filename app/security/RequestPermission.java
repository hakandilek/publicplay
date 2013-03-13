package security;

import java.util.List;

import play.mvc.Http.Request;
import be.objectify.deadbolt.core.models.Permission;

public interface RequestPermission {

	boolean isAllowed(Request request, List<? extends Permission> permissions);

}
