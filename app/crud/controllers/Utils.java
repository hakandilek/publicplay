package crud.controllers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
public class Utils {

	private static Class<Annotation>[] ANNOTATION_CLASSES = new Class [] {
		javax.persistence.Id.class,
		javax.persistence.EmbeddedId.class,
		javax.persistence.Basic.class,
		play.data.validation.Constraints.Required.class,
	};
	
	private static Class<Annotation>[] KEY_ANNOTATION_CLASSES = new Class [] {
		javax.persistence.Id.class,
		javax.persistence.EmbeddedId.class,
	};
	
	public static boolean isFieldAnnotated(Field f) {
		for (Class<Annotation> ann : ANNOTATION_CLASSES) {
			if (f.isAnnotationPresent(ann))
				return true;
		}
		return false;
	}

	public static boolean isKeyField(Field f) {
		for (Class<Annotation> ann : KEY_ANNOTATION_CLASSES) {
			if (f.isAnnotationPresent(ann))
				return true;
		}
		return false;
	}
}
