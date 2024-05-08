package org.example.onlinepharmy.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.onlinepharmy.domain.Role;

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
