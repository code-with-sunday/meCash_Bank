package com.sunday.mecashbank.authService;


import com.sunday.mecashbank.DTO.request.LoginRequest;
import com.sunday.mecashbank.DTO.request.UserSignUpRequest;
import com.sunday.mecashbank.DTO.response.AuthResponse;
import com.sunday.mecashbank.enums.ACCOUNT_STATUS;
import com.sunday.mecashbank.enums.CURRENCY_TYPE;
import com.sunday.mecashbank.enums.ROLE;
import com.sunday.mecashbank.exception.UserAlreadyExistException;
import com.sunday.mecashbank.mapper.UserMapper;
import com.sunday.mecashbank.model.Account;
import com.sunday.mecashbank.model.User;
import com.sunday.mecashbank.repository.AccountRepository;
import com.sunday.mecashbank.repository.UserRepository;
import com.sunday.mecashbank.security.JwtProvider;
import com.sunday.mecashbank.utils.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthUserDeatilsImpl implements AuthUserDetails{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsImpl customUserDetails;
    private final UserMapper userMapper;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AccountRepository accountRepository;



    @Override

    public AuthResponse userSignup(UserSignUpRequest userSignUpRequest) throws Exception {
        User user = new User();
        user.setEmail(userSignUpRequest.getEmail());

        User isEmailExist  = userRepository.findByEmail(user.getEmail());

        if(isEmailExist != null){
            throw new UserAlreadyExistException("Email already exist with another account");
        }

        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        User savedUser = userRepository.save(user);

        String accountNumber = generateUniqueAccountNumber();

        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setAccountStatus(ACCOUNT_STATUS.ACTIVE);
        account.setAccountNumber(accountNumber);
        account.setUser(savedUser);
        account.setCurrency(CURRENCY_TYPE.NGN);
        account.prePersist();
        Account savedAccount = accountRepository.save(account);

        savedUser.setAccounts(savedAccount.getUser().getAccounts());
        user.prePersist();
        User savedUser2 = userRepository.save(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Welcome " + user.getEmail());
        authResponse.setMessage("Register success, you account number is " + savedAccount.getAccountNumber());
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }

    @Override
    public AuthResponse AdminSignup(UserSignUpRequest userSignUpRequest) throws Exception {
        User user = new User();
        user.setEmail(userSignUpRequest.getEmail());

        User isEmailExist  = userRepository.findByEmail(user.getEmail());

        if(isEmailExist != null){
            throw new UserAlreadyExistException("Email already exist with another account");
        }

        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
        user.setRole(ROLE.ADMIN);
        User savedUser = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setTitle("Welcome " + user.getEmail());
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());

        return authResponse;
    }


    @Override
    public AuthResponse signIn(LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? String.valueOf(ROLE.USER) : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login success");
        authResponse.setTitle(jwt);
        authResponse.setRole(ROLE.valueOf(role));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password....");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }
    private String generateUniqueAccountNumber() {
        String accountNumber;
        boolean exists;
        do {
            accountNumber = accountNumberGenerator.generateAccountNumber();
            exists = accountRepository.existsByAccountNumber(accountNumber);
        } while (exists);

        return accountNumber;
    }
}
