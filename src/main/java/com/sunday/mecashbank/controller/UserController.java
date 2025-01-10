package com.sunday.mecashbank.controller;

import com.sunday.mecashbank.DTO.request.UserProfileRequest;
import com.sunday.mecashbank.DTO.response.ApiResponse;
import com.sunday.mecashbank.DTO.response.UserResponse;
import com.sunday.mecashbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/update-profile")
    public ApiResponse<UserResponse> updateProfile(@RequestBody UserProfileRequest userProfileRequest) {
        UserResponse userResponse = userService.updateProfile(userProfileRequest);
        return new ApiResponse<>(true, "201", "Profile created successfully", userResponse);
    }
}
