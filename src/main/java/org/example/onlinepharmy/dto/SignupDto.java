package org.example.onlinepharmy.dto;

import lombok.*;
import org.example.onlinepharmy.domain.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupDto {
    private String email;
    private String username;
    private String password;
    private String location;
    private List<Role> roles;

}
