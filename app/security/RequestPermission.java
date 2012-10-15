package security;

import java.util.List;

import be.objectify.deadbolt.models.Permission;
import play.mvc.Http.Request;

public interface RequestPermission {

	boolean isAllowed(Request request, List<? extends Permission> permissions);

}
