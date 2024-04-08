package com.sxhta.cloud.common.component;

import jakarta.validation.Validator;

public interface BeanValidatorComponent {

    void validateWithException(Validator validator, Object object, Class<?>... groups);
}
