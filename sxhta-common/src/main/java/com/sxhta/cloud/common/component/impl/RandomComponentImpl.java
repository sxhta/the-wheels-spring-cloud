package com.sxhta.cloud.common.component.impl;

import com.sxhta.cloud.common.component.RandomComponent;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Singleton
@Component
public class RandomComponentImpl implements RandomComponent , Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public Integer randomCount(Integer start, Integer end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}