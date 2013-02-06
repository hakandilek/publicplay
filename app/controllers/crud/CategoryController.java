package controllers.crud;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.springframework.util.ReflectionUtils;

import com.avaje.ebean.Page;

import models.Category;
import models.dao.CategoryDAO;
import play.mvc.Call;
import play.mvc.Content;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.crud.Parameters;
import controllers.routes;

public class CategoryController extends CRUDController<String, Category> {

	@Inject
	public CategoryController(CategoryDAO dao) {
		super(dao, form(Category.class), String.class, Category.class, 20, "name");
	}

	@Override
	protected String templateForForm() {
		return "admin.categoryForm";
	}

	@Override
	protected String templateForList() {
		return "admin.categoryList";
	}

	@Override
	protected String templateForShow() {
		return "admin.categoryShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Admin.categoryList(0);
	}
	
	public Result list(int page) {
		Page<Category> p = getDao().page(page, pageSize(), orderBy());
		return ok(templateForList(), with(Page.class, p));
	}
	
	protected Result ok(String template, Parameters params) {
		Content content;
		try {
			content = call("views.html." + template, "render", params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return internalServerError(templateNotFound(template, params));
		}
		return ok(content);
	}

	@SuppressWarnings("unchecked")
	private <R> R call(String className, String methodName, Parameters params) throws ClassNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
		Object result = null;
		Class<?> clazz = classLoader.loadClass(className);
		Class<?>[] paramTypes = params.types();
		Object[] paramValues = params.values();
		Method method = ReflectionUtils.findMethod(clazz, methodName,
				paramTypes);
		result = ReflectionUtils.invokeMethod(method, null, paramValues);
		return (R) result;
	}

	private String templateNotFound(String template, Parameters params) {
		StringBuilder sb = new StringBuilder("Template ");
		sb.append(template).append("(");
		Class<?>[] types = params.types();
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (i != 0) sb.append(", ");
			sb.append(type.getSimpleName());
		}
		sb.append(") is not found");
		return sb.toString();
	}





}
