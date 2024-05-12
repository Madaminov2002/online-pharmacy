package org.example.onlinepharmy.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.onlinepharmy.domain.User;
import org.example.onlinepharmy.dto.LoginDto;
import org.example.onlinepharmy.dto.SignupDto;
import org.example.onlinepharmy.updateDto.UserUpdateDto;
import org.example.onlinepharmy.jwt.JwtResponse;
import org.example.onlinepharmy.service.AuthService;
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

    @SneakyThrows
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
    @Operation(summary = "Update user", description = "Uses for update users and only for SUPER ADMIN's")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateUser(updateDto));
    }

    @GetMapping("/checkingUser/user-id/{id}")
    @Operation(summary = "Checking user", description = "Uses for checking user")
    public ResponseEntity<User> checkingUser(@RequestParam("password") String password, @PathVariable("id") Long userId) {
        return ResponseEntity.ok(authService.checking(password, userId));
    }

}
