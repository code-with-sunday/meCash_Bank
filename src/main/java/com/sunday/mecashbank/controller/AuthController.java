package com.sunday.mecashbank.controller;

import com.sunday.mecashbank.DTO.request.LoginRequest;
import com.sunday.mecashbank.DTO.request.UserSignUpRequest;
import com.sunday.mecashbank.DTO.response.ApiResponse;
import com.sunday.mecashbank.DTO.response.AuthResponse;
import com.sunday.mecashbank.authService.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUserDetails authUserDetails;

    @PostMapping("/signup")
    public ApiResponse<AuthResponse> userSignup(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception {
        AuthResponse authResponse = authUserDetails.userSignup(userSignUpRequest);
        return new ApiResponse<>(true, "201", "User created successfully", authResponse);
    }

    @PostMapping("/admin/signup")
    public ApiResponse<AuthResponse> AdminSignup(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception {
        AuthResponse authResponse = authUserDetails.AdminSignup(userSignUpRequest);
        return new ApiResponse<>(true, "201", "Admin user created successfully", authResponse);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authUserDetails.signIn(loginRequest);
        return new ApiResponse<>(true, "201", "User logged in successfully", authResponse);
    }
}