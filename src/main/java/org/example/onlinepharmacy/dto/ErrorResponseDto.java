package org.example.onlinepharmacy.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrorResponseDto {
    private String message;
    private Integer code;
    private HttpStatus status;
}
