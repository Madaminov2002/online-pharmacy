package org.example.onlinepharmy.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDto {
    private String email;
    private String username;
    private String password;
}
