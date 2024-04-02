package com.sxhta.cloud.remote.util;

public final class AdminChecker {

    private static final Long ADMIN_USER_ID = 1L;

    private static final Long ADMIN_ROLE_ID = 1L;

    public static Boolean isAdminUser(Long userId) {
        return userId != null && userId.equals(ADMIN_USER_ID);
    }

    public static Boolean isAdminRole(Long roleId) {
        return roleId != null && roleId.equals(ADMIN_ROLE_ID);
    }
}
