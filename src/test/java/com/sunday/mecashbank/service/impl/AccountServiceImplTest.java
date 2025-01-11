//package com.sunday.mecashbank.service.impl;
//
//import com.sunday.mecashbank.DTO.request.AccountRequest;
//import com.sunday.mecashbank.DTO.request.DepositRequest;
//import com.sunday.mecashbank.DTO.request.TransferRequest;
//import com.sunday.mecashbank.DTO.request.WithdrawRequest;
//import com.sunday.mecashbank.DTO.response.AccountResponse;
//import com.sunday.mecashbank.enums.CURRENCY_TYPE;
//import com.sunday.mecashbank.exception.InsufficientFundsException;
//import com.sunday.mecashbank.exception.UnAuthorizedException;
//import com.sunday.mecashbank.exception.UserAlreadyExistException;
//import com.sunday.mecashbank.exception.UserNotFoundException;
//import com.sunday.mecashbank.model.Account;
//import com.sunday.mecashbank.model.Transaction;
//import com.sunday.mecashbank.model.User;
//import com.sunday.mecashbank.repository.AccountRepository;
//import com.sunday.mecashbank.repository.TransactionRepository;
//import com.sunday.mecashbank.repository.UserRepository;
//import com.sunday.mecashbank.service.Impl.AccountServiceImpl;
//import com.sunday.mecashbank.service.TransactionService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import javax.security.auth.login.AccountNotFoundException;
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static com.sunday.mecashbank.enums.CURRENCY_TYPE.USD;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AccountServiceImplTest {
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private TransactionService transactionService;
//
//    @Mock
//    private SecurityContext securityContext;
//
//    @Mock
//    private Authentication authentication;
//
//    @InjectMocks
//    private AccountServiceImpl accountService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        SecurityContextHolder.setContext(securityContext);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getPrincipal()).thenReturn("user@example.com");
//    }
//
//    @Test
//    void viewAccountBalance_success() throws AccountNotFoundException {
//        User user = new User();
//        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
//
//        Account account = new Account();
//        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(account));
//
//        AccountResponse response = accountService.viewAccountBalance("123456");
//
//        assertNotNull(response);
//        assertEquals("123456", response.getAccountNumber());
//        assertEquals(BigDecimal.valueOf(1000), response.getBalance());
//        assertEquals("USD", response.getCurrency());
//    }
//
//    @Test
//    void deposit_success() throws AccountNotFoundException {
//        User user = new User();
//        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
//
//        Account account = new Account("123456", BigDecimal.valueOf(1000), CURRENCY_TYPE.NGN);
//        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(account));
//
//        DepositRequest request = new DepositRequest(BigDecimal.valueOf(500));
//
//        AccountResponse response = accountService.deposit("123456", request);
//
//        assertEquals(BigDecimal.valueOf(1500), response.getBalance());
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }
//
//    @Test
//    void withdraw_success() throws AccountNotFoundException {
//        User user = new User();
//        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
//
//        Account account = new Account("123456", BigDecimal.valueOf(1000), USD);
//        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(account));
//
//        WithdrawRequest request = new WithdrawRequest(BigDecimal.valueOf(500));
//
//        AccountResponse response = accountService.withdraw("123456", request);
//
//        assertEquals(BigDecimal.valueOf(500), response.getBalance());
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }
//
//    @Test
//    void transfer_success() throws AccountNotFoundException {
//        User user = new User();
//        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
//
//        Account fromAccount = new Account("1234561112", BigDecimal.valueOf(1000), USD);
//        Account toAccount = new Account("6543212321", BigDecimal.valueOf(500), USD);
//
//        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(fromAccount));
//        when(accountRepository.findByAccountNumber("654321")).thenReturn(Optional.of(toAccount));
//
//        TransferRequest request = new TransferRequest("654321", BigDecimal.valueOf(300));
//
//        AccountResponse response = accountService.transfer("123456", request);
//
//        assertEquals(BigDecimal.valueOf(700), fromAccount.getBalance());
//        assertEquals(BigDecimal.valueOf(800), toAccount.getBalance());
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }
//
//    @Test
//    void withdraw_insufficientFunds() {
//        User user = new User();
//        when(userRepository.findByEmail("user@example.com")).thenReturn(user);
//
//        Account account = new Account("6543212321", BigDecimal.valueOf(100));
//        when(accountRepository.findByAccountNumber("6543212321")).thenReturn(Optional.of(account));
//
//        WithdrawRequest request = new WithdrawRequest(BigDecimal.valueOf(500));
//
//        assertThrows(InsufficientFundsException.class,
//                () -> accountService.withdraw("6543212321", request),
//                "Expected InsufficientFundsException when balance is insufficient");
//    }
//
//    @Test
//    void viewAccountBalance_accountNotFound() {
//        when(accountRepository.findByAccountNumber("999999")).thenReturn(Optional.empty());
//
//        assertThrows(AccountNotFoundException.class,
//                () -> accountService.viewAccountBalance("999999"),
//                "Expected AccountNotFoundException when account does not exist");
//    }
//
//    @Test
//    void createAccount_SuccessfulAccountCreation_ReturnsAccountResponse() {
//        // Given
//        AccountRequest accountRequest = AccountRequest.builder()
//                .accountNumber("1234567890")
//                .currency("NGN")
//                .accountStatus("ACTIVE")
//                .balance(BigDecimal.valueOf(1000.00))
//                .userId(1L)
//                .build();
//
//        User mockUser = User.builder().id(1L).email("test@example.com").build();
//
//        Account mockAccount = Account.builder()
//                .accountNumber("1234567890")
//                .currency(CURRENCY_TYPE.NGN)
//                .accountStatus(ACCOUNT_STATUS.ACTIVE)
//                .balance(BigDecimal.valueOf(1000.00))
//                .user(mockUser)
//                .build();
//
//        // Mock Authentication and Security Context
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.isAuthenticated()).thenReturn(true);
//
//        // Mock User Repository and Account Repository
//        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
//        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);
//
//        // When
//        AccountResponse response = accountService.createAccount(accountRequest);
//
//        // Then
//        assertNotNull(response);
//        assertEquals("1234567890", response.getAccountNumber());
//        assertEquals(CURRENCY_TYPE.NGN, response.getCurrency());
//        assertEquals(BigDecimal.valueOf(1000.00), response.getBalance());
//
//        verify(accountRepository, times(1)).save(any(Account.class));
//    }
//
//    @Test
//    void createAccount_AuthenticationFailure_ThrowsUnAuthorizedException() {
//        // Given
//        when(securityContext.getAuthentication()).thenReturn(null);
//
//        AccountRequest accountRequest = new AccountRequest();
//
//        // When & Then
//        assertThrows(UnAuthorizedException.class, () -> accountService.createAccount(accountRequest));
//    }
//
//    @Test
//    void createAccount_UserNotFound_ThrowsUserNotFoundException() {
//        // Given
//        AccountRequest accountRequest = AccountRequest.builder().userId(99L).build();
//
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(userRepository.findById(99L)).thenReturn(Optional.empty());
//
//        // When & Then
//        assertThrows(UserNotFoundException.class, () -> accountService.createAccount(accountRequest));
//    }
//}
