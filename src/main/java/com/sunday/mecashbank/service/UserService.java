package com.sunday.mecashbank.service;

import com.sunday.mecashbank.DTO.request.UserProfileRequest;
import com.sunday.mecashbank.DTO.request.UserRequest;
import com.sunday.mecashbank.DTO.response.UserResponse;

public interface UserService {
    UserResponse updateProfile(UserProfileRequest userProfileRequest);
    UserResponse createAdmin (UserRequest userRequest);

}
