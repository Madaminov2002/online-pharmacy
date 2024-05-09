package org.example.onlinepharmy.dto;

import java.net.URI;
import java.net.URL;
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
