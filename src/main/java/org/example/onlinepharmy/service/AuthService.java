package org.example.onlinepharmy.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.User;
import org.example.onlinepharmy.dto.LoginDto;
import org.example.onlinepharmy.dto.SignupDto;
import org.example.onlinepharmy.jwt.JwtProvider;
import org.example.onlinepharmy.jwt.JwtResponse;
import org.example.onlinepharmy.repo.UserRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public JwtResponse signUp(SignupDto signupDto){
        if (userRepository.existsByUsername(signupDto.getUsername())){
            throw new RuntimeException("Username is already exists!");
        }
        if (userRepository.existsByEmail(signupDto.getEmail())){
            throw new RuntimeException("Email is already exists!");
        }

        User user = User.builder()
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtProvider.generate(savedUser);

        return new JwtResponse(user.getId(), token);
    }


    public JwtResponse login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail());

        if (!userRepository.existsByEmail(loginDto.getEmail())){
            throw new RuntimeException("This email not found " + loginDto.getEmail());
        }

        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new RuntimeException("Wrong password try again");
        }

        String token = jwtProvider.generate(user);
        return new JwtResponse(user.getId(), token);

    }



}
