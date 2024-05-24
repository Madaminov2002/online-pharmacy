package org.example.onlinepharmacy.service;

import java.util.Optional;
import org.example.onlinepharmacy.updatedto.UserUpdateDto;
import org.example.onlinepharmacy.exception.EmailAlreadyExistsException;
import org.example.onlinepharmacy.exception.EmailNotFoundException;
import org.example.onlinepharmacy.exception.PasswordIncorrectException;
import org.example.onlinepharmacy.exception.UserNotFoundException;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.LoginDto;
import org.example.onlinepharmacy.dto.SignupDto;
import org.example.onlinepharmacy.jwt.JwtProvider;
import org.example.onlinepharmacy.jwt.JwtResponse;
import org.example.onlinepharmacy.repo.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void signUpThrowEmailAlreadyExistsExceptionEmailExists() {
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("test@gmail.com");

        when(userRepository.existsByEmail(signupDto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> authService.signUp(signupDto));
    }

    @Test
    void loginThrowEmailNotFoundExceptionWhenEmailNotExist() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setPassword("password");

        when(userRepository.existsByEmail(loginDto.getEmail())).thenReturn(false);

        assertThrows(EmailNotFoundException.class, () -> authService.login(loginDto));
    }

    @Test
    void loginThrowPasswordIncorrectExceptionWhenPasswordNotMatch() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        when(userRepository.existsByEmail(loginDto.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(PasswordIncorrectException.class, () -> authService.login(loginDto));
    }

    @Test
    void loginReturnJwtResponseCredentialsAreValid() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        when(userRepository.existsByEmail(loginDto.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtProvider.generate(any(User.class))).thenReturn("jwt-token");

        JwtResponse jwtResponse = authService.login(loginDto);

        assertEquals(1L, jwtResponse.getUserId());
        assertEquals("jwt-token", jwtResponse.getAccessToken());
    }

    @Test
    void updateUserThrowUserNotFoundExceptionUserDoesNotExist() {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setId(1L);

        when(userRepository.findById(updateDto.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.updateUser(updateDto));
    }

    @Test
    void updateUserUpdateAndReturnUser() {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setId(1L);
        updateDto.setUsername("newusername");

        User user = new User();
        user.setId(1L);
        user.setUsername("oldusername");
        user.setEmail("test@gmail.com");

        when(userRepository.findById(updateDto.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = authService.updateUser(updateDto);

        assertEquals("newusername", updatedUser.getUsername());

    }
}