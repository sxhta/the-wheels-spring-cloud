package com.sxhta.cloud.common.component.impl;

import com.sxhta.cloud.common.component.RandomComponent;
import jakarta.inject.Singleton;
import org.springframework.stereotype.Component;

@Singleton
@Component
public class RandomComponentImpl implements RandomComponent {

    @Override
    public Integer randomCount(Integer start, Integer end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }
}