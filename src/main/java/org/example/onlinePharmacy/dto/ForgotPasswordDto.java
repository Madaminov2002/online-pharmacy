package org.example.onlinePharmacy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForgotPasswordDto {
    private String email;
    private String password;
}