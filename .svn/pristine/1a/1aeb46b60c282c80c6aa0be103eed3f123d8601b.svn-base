package com.sf.social.signinmvc.user.validation;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidatorContext;

/**
 * @author Petri Kainulainen
 */
public final class ValidatorUtil {
	private ValidatorUtil() {
	}

    public static void addValidationError(final String field, 
    						final ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(
        		context.getDefaultConstraintMessageTemplate())
                .addNode(field)
                .addConstraintViolation();
    }

    public static Object getFieldValue(final Object object, 
    				final String fieldName) 
    				throws NoSuchFieldException, IllegalAccessException {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }
}
