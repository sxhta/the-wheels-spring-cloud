package com.wheels.cloud.frontend.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Collection;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public final class UserDetailsVo implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final FrontUser frontUser;

    private Long loginTime;

    private Long expireTime;

    private String token;

    private Long userId;

    private String account;

    public UserDetailsVo(FrontUser frontUser) {
        this.frontUser = frontUser;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return frontUser.getPassword();
    }

    @Override
    public String getUsername() {
        return frontUser.getAccount();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public Boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @JsonIgnore
    @Override
    public Boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @JsonIgnore
    @Override
    public Boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @JsonIgnore
    @Override
    public Boolean isEnabled() {
        return true;
    }
}
