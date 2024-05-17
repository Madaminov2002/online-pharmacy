package org.example.onlinePharmacy.service;

import io.jsonwebtoken.Claims;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.onlinePharmacy.projection.UserDtoProjection;
import org.example.onlinePharmacy.advice.exception.EmailAlreadyExistsException;
import org.example.onlinePharmacy.advice.exception.EmailNotFoundException;
import org.example.onlinePharmacy.advice.exception.PasswordIncorrectException;
import org.example.onlinePharmacy.advice.exception.UserNotEnableForChangingPasswordException;
import org.example.onlinePharmacy.advice.exception.UserNotFoundException;
import org.example.onlinePharmacy.domain.ForgotPassword;
import org.example.onlinePharmacy.domain.Role;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.domain.Verification;
import org.example.onlinePharmacy.dto.ChangePasswordDto;
import org.example.onlinePharmacy.dto.LoginDto;
import org.example.onlinePharmacy.dto.SendMailDto;
import org.example.onlinePharmacy.dto.SignupDto;
import org.example.onlinePharmacy.jwt.JwtProvider;
import org.example.onlinePharmacy.jwt.JwtResponse;
import org.example.onlinePharmacy.repo.ForgotPasswordRepository;
import org.example.onlinePharmacy.repo.UserRepository;
import org.example.onlinePharmacy.repo.VerificationRepository;
import org.example.onlinePharmacy.updateDto.UserUpdateDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;
    private final JavaMailSender mailSender;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public User getUserEntity(SignupDto signupDto) {
        return User.builder()
                .username(signupDto.getUsername())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .roles(List.of(Role.builder().id(3L).name("USER").build()))
                .build();
    }

    @SneakyThrows
    public JwtResponse signUp(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new EmailAlreadyExistsException(signupDto.getEmail());
        }

        User user = getUserEntity(signupDto);

        User savedUser = userRepository.save(user);

        String token = jwtProvider.generate(savedUser);

        saveToVerification(token);

        return new JwtResponse(user.getId(), token, signupDto.getLocation());
    }

    @SneakyThrows
    public void saveToVerification(String token) {
        Claims claims = jwtProvider.parse(token);
        String password = claims.get("password", String.class);
        sendMail(password);
        verificationRepository.save(
                Verification.builder()
                        .email(claims.getSubject())
                        .password(password)
                        .expiryTime(LocalTime.now().plusHours(2L))
                        .build()
        );
    }

    public User checking(String password, Long userId) {
        User user = userRepository.findById(userId).get();
        Verification verification = verificationRepository.findByEmail(user.getEmail());
        if (password.equals(verification.getPassword()) && verification.getExpiryTime().isAfter(LocalTime.now())) {
            return user;
        }
        throw new PasswordIncorrectException(password);
    }


    public JwtResponse login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail());

        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            throw new EmailNotFoundException(loginDto.getEmail());
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordIncorrectException(loginDto.getPassword());
        }

        String token = jwtProvider.generate(user);
        return new JwtResponse(user.getId(), token, loginDto.getLocation());

    }

    public User updateUser(UserUpdateDto updateDto) {

        User user = userRepository.findById(updateDto.getId()).orElse(null);


        if (user == null) {
            throw new UserNotFoundException(String.valueOf(updateDto.getId()));
        }

        if (updateDto.getUsername() != null) {
            user.setUsername(updateDto.getUsername());
        }
        if (updateDto.getEmail() != null) {
            user.setEmail(updateDto.getEmail());
        }
        if (updateDto.getPassword() != null) {
            user.setPassword(updateDto.getPassword());
        }

        return userRepository.save(user);
    }

    /**
     * This method only for mailtrap!
     *
     * @param password
     */
    @Async
    public void sendMail(String password) {
        SendMailDto dto = SendMailDto.builder()
                .to("admin@example.com")
                .content(password)
                .subject("Password").build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(dto.getContent());
        mailMessage.setSubject(dto.getSubject());
        mailMessage.setSentDate(new Date());
        mailMessage.setTo(dto.getTo());
        mailSender.send(mailMessage);
    }

    public ForgotPassword generatePasswordAndSaveToForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        int password = new Random().nextInt(100000, 1000000);
        sendMail(String.valueOf(password));
        return forgotPasswordRepository.save(
                ForgotPassword.builder()
                        .email(email)
                        .password(String.valueOf(password))
                        .build()
        );
    }

    public UserDtoProjection checkUserEnabledFromForgotPassword(ChangePasswordDto dto) {
        Boolean enabled = forgotPasswordRepository.checkByEmailEnabled(dto.getEmail());
        if (enabled == null) {
            throw new UserNotEnableForChangingPasswordException(dto.getEmail());
        }
        userRepository.changePassword(passwordEncoder.encode(dto.getNewPassword()), dto.getEmail());
        return userRepository.getChangedPasswordUser(dto.getEmail());
    }

}
