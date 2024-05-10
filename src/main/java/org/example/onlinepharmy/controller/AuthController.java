package org.example.onlinepharmy.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.User;
import org.example.onlinepharmy.dto.LoginDto;
import org.example.onlinepharmy.dto.SignupDto;
import org.example.onlinepharmy.dto.UserUpdateDto;
import org.example.onlinepharmy.jwt.JwtResponse;
import org.example.onlinepharmy.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Auth APIs",
        description = "This class uses for authentication"
)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "SignUp user", description = "Uses for registration and generating token")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(authService.signUp(signupDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Uses for login account and generating token")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update users", description = "Uses for update users and only for ADMIN")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateUser(updateDto));
    }


}
