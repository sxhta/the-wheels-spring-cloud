package com.sxhta.cloud.common.utils.encrypt;

import com.google.common.hash.Hashing;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class EncryptUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static String generateEntityHash(String hashCode) {
        return Hashing.sha256()
                .hashString(hashCode, StandardCharsets.UTF_8)
                .toString();
    }
}
