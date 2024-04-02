package com.sxhta.cloud.common.utils.bean;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.io.Serial;
import java.io.Serializable;

/**
 * bean对象属性验证
 */
public class BeanValidators implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static void validateWithException(Validator validator, Object object, Class<?>... groups)
            throws ConstraintViolationException {
        final var constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
