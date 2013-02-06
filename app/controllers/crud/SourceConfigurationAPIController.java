package controllers.crud;

import static play.libs.Json.toJson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import models.SourceConfiguration;
import models.dao.SourceConfigurationDAO;
import play.mvc.Result;
import play.utils.crud.APIController;

import com.google.common.collect.ImmutableMap;


public class SourceConfigurationAPIController extends APIController<Long, SourceConfiguration> {

	protected SourceConfigurationDAO sourceConfigurationDAO;

	@Inject
	public SourceConfigurationAPIController(SourceConfigurationDAO sourceConfigurationDAO) {
		super(sourceConfigurationDAO);
		this.sourceConfigurationDAO = sourceConfigurationDAO;
	}

	@Override
	public Result create() {
		
		Result check = checkRequired("sourceKey", "descriptionSelector",
				"discountPriceSelector", "imageLinkSelector", "nameSelector",
				"priceSelector");
		if (check != null) {
			return check;
		}

		String sourceKey = jsonText("sourceKey");
		String descriptionSelector = jsonText("descriptionSelector");
		String discountPriceSelector = jsonText("discountPriceSelector");
		String imageLinkSelector = jsonText("imageLinkSelector");
		String nameSelector = jsonText("nameSelector");
		String priceSelector = jsonText("priceSelector");

		SourceConfiguration m = new SourceConfiguration();
		m.setSourceKey(sourceKey);
		m.setDescriptionSelector(descriptionSelector);
		m.setDiscountPriceSelector(discountPriceSelector);
		m.setNameSelector(nameSelector);
		m.setImageLinkSelector(imageLinkSelector);
		m.setPriceSelector(priceSelector);
		Long key = dao.create(m);
		
		return created(toJson(ImmutableMap.of(
				"status", "OK",
				"key", key,
				"data", m)));
	}

	protected <S> void setField(S s, String fieldName, String value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> cls = s.getClass();
		Method method = cls.getMethod("set" + fieldName, String.class);
		method.invoke(s, value);
	}

}
