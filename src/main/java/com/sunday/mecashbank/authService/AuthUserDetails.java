package com.sunday.mecashbank.authService;

import com.sunday.mecashbank.DTO.request.LoginRequest;
import com.sunday.mecashbank.DTO.request.UserSignUpRequest;
import com.sunday.mecashbank.DTO.response.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthUserDetails {

    AuthResponse signIn(@RequestBody LoginRequest loginRequest);
    AuthResponse userSignup(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception;
    AuthResponse AdminSignup(@RequestBody UserSignUpRequest userSignUpRequest) throws Exception;
}

