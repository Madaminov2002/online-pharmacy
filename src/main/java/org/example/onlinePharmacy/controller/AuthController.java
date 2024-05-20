package org.example.onlinePharmacy.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.projection.ForgotPasswordProjection;
import org.example.onlinePharmacy.projection.UserDtoProjection;
import org.example.onlinePharmacy.advice.exception.PasswordIncorrectException;
import org.example.onlinePharmacy.domain.ForgotPassword;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.dto.ChangePasswordDto;
import org.example.onlinePharmacy.dto.ForgotPasswordDto;
import org.example.onlinePharmacy.dto.LoginDto;
import org.example.onlinePharmacy.dto.SignupDto;
import org.example.onlinePharmacy.jwt.JwtResponse;
import org.example.onlinePharmacy.repo.ForgotPasswordRepository;
import org.example.onlinePharmacy.service.AuthService;
import org.example.onlinePharmacy.updateDto.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication API",
        description = "Uses in authentication"
)
public class AuthController {

    private final AuthService authService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    @PostMapping("/signup")
    @Operation(summary = "User signup", description = "Uses in registration and token generation")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authService.signUp(signupDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Uses for login account and token generation")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Update user", description = "Uses for update users and only for SUPER ADMIN")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateUser(updateDto));
    }

    @GetMapping("/checkingUser/user-id/{id}")
    @Operation(summary = "Checking user", description = "Uses for checking verification user")
    public ResponseEntity<User> checkingUser(@RequestParam("password") String password, @PathVariable("id") Long userId) {
        return ResponseEntity.ok(authService.checking(password, userId));
    }

    @GetMapping("/emailForForgotPassword")
    @Operation(summary = "Forgot password", description = "Uses for forgot password and sending code to email ")
    public ResponseEntity<ForgotPassword> emailForForgotPassword(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.generatePasswordAndSaveToForgotPassword(email));
    }

    @PostMapping("/checkForgotPassword")
    @Operation(summary = "Check Forgot password", description = "Uses for checking forgot password")
    private ResponseEntity<ForgotPasswordProjection> checkForgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        return ResponseEntity.ok(checkForgotPassword(forgotPasswordDto.getPassword(), forgotPasswordDto.getEmail()));
    }

    @PostMapping("/changing-password")
    @Operation(summary = "Change password", description = "Uses for changing account password")
    public ResponseEntity<UserDtoProjection> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok(authService.checkUserEnabledFromForgotPassword(changePasswordDto));
    }

    public ForgotPasswordProjection checkForgotPassword(String password, String email) {
        var forgotPasswordByEmailAndPassword = forgotPasswordRepository.findForgotPasswordByEmailAndPassword(email, password);
        if (forgotPasswordByEmailAndPassword == null) {
            throw new PasswordIncorrectException(password);
        }
        forgotPasswordRepository.updateEnabledToTrue(email);
        return forgotPasswordByEmailAndPassword;
    }

}
