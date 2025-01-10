package com.sunday.mecashbank.service.Impl;

import com.sunday.mecashbank.DTO.request.UserProfileRequest;
import com.sunday.mecashbank.DTO.request.UserRequest;
import com.sunday.mecashbank.DTO.response.UserResponse;
import com.sunday.mecashbank.enums.ACCOUNT_STATUS;
import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import com.sunday.mecashbank.enums.ROLE;
import com.sunday.mecashbank.exception.UnAuthorizedException;
import com.sunday.mecashbank.exception.UserAlreadyExistException;
import com.sunday.mecashbank.exception.UserNotFoundException;
import com.sunday.mecashbank.mapper.UserMapper;
import com.sunday.mecashbank.model.Account;
import com.sunday.mecashbank.model.User;
import com.sunday.mecashbank.repository.AccountRepository;
import com.sunday.mecashbank.repository.UserRepository;
import com.sunday.mecashbank.security.JwtProvider;
import com.sunday.mecashbank.service.UserService;
import com.sunday.mecashbank.utils.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountRepository accountRepository;


    @Override
    public UserResponse updateProfile(UserProfileRequest userProfileRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new UserNotFoundException("User with email " + email + " already exists");
        }

        user.setFirstName(userProfileRequest.getFirstName());
        user.setLastName(userProfileRequest.getLastName());
        user.setUsername(userProfileRequest.getUsername());
        User savedUser = userRepository.save(user);

        userRepository.save(savedUser);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse createAdmin(UserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        User currentUser = userRepository.findByEmail(email);

        if (currentUser == null || currentUser.getRole() != ROLE.ADMIN) {
            throw new UnAuthorizedException("Only Admins can create users.");
        }

        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new UserAlreadyExistException("User with email " + userRequest.getEmail() + " already exists.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }



}

