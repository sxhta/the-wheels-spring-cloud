package com.sxhta.cloud.common.random;

import java.util.Random;

public final class RandomUtil {

    /**
     * 可能以后会变，所以复制了一份
     */
    public static String generateInviteCode() {
        final var random = new Random();
        final var num = random.nextInt(999999);
        return String.valueOf(num);
    }
}

