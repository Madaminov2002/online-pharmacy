package org.example.onlinePharmacy.updateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private Long id;
    private String username;
    private String password;
    private String email;
}