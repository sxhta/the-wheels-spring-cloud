package com.sxhta.cloud.system.backend.config;

import lombok.Getter;

@Getter
public enum ConfigKeys {

    /**
     * 正常
     */
    FILE_HOST("file_host");

    private final String value;

    ConfigKeys(String value) {
        this.value = value;
    }
}
