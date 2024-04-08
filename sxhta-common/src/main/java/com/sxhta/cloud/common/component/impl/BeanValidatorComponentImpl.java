package com.sxhta.cloud.common.component.impl;

import com.sxhta.cloud.common.component.BeanValidatorComponent;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * bean对象属性验证
 */
@Singleton
@Component
public class BeanValidatorComponentImpl implements BeanValidatorComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void validateWithException(Validator validator, Object object, Class<?>... groups)
            throws ConstraintViolationException {
        final var constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
