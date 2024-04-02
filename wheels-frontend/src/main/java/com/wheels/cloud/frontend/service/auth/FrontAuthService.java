package com.wheels.cloud.frontend.service.auth;

import com.wheels.cloud.frontend.request.auth.LoginRequest;
import com.wheels.cloud.frontend.request.auth.RegisterRequest;
import com.wheels.cloud.frontend.response.LoginResponse;
import com.wheels.cloud.frontend.response.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface FrontAuthService {

    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);

    Boolean checkPassword(String requestPassword, String dbPassword);

    Boolean logout(HttpServletRequest request);
}
