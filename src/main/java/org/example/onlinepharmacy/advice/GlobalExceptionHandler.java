package org.example.onlinepharmacy.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.example.onlinepharmacy.exception.AdminNotFoundException;
import org.example.onlinepharmacy.exception.AvailableMedicineNotFoundException;
import org.example.onlinepharmacy.exception.DistrictNotFoundException;
import org.example.onlinepharmacy.exception.EmailAlreadyExistsException;
import org.example.onlinepharmacy.exception.EmailNotFoundException;
import org.example.onlinepharmacy.exception.IsNotEnoughMoneyException;
import org.example.onlinepharmacy.exception.MedicineNotFoundException;
import org.example.onlinepharmacy.exception.MedicineNotFoundFromAvailableException;
import org.example.onlinepharmacy.exception.PasswordIncorrectException;
import org.example.onlinepharmacy.exception.PharmacyIsNotYoursException;
import org.example.onlinepharmacy.exception.UserNotEnableForChangingPasswordException;
import org.example.onlinepharmacy.exception.UserNotFoundException;
import org.example.onlinepharmacy.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> userNotFound(UserNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpServletResponse.SC_NOT_FOUND)
                        .build()
        );
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponseDto> incorrectPassword(PasswordIncorrectException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> emailAlreadyExists(EmailAlreadyExistsException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.CONFLICT)
                        .code(HttpServletResponse.SC_CONFLICT)
                        .build()
        );
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> emailNotFound(EmailNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(DistrictNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> districtNotFound(DistrictNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> adminNotFound(AdminNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(PharmacyIsNotYoursException.class)
    public ResponseEntity<ErrorResponseDto> isNotYours(PharmacyIsNotYoursException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(MedicineNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> medicineNotFound(MedicineNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(MedicineNotFoundFromAvailableException.class)
    public ResponseEntity<ErrorResponseDto> medicineNotFoundFromAvailable(MedicineNotFoundFromAvailableException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(AvailableMedicineNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> availableMedicineNotFound(AvailableMedicineNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(UserNotEnableForChangingPasswordException.class)
    public ResponseEntity<ErrorResponseDto> userNotEnabled(UserNotEnableForChangingPasswordException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(IsNotEnoughMoneyException.class)
    public ResponseEntity<String> isNotEnough(IsNotEnoughMoneyException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }
}
