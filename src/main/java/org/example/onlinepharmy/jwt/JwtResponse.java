package org.example.onlinepharmy.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private LocalDateTime dateTime = LocalDateTime.now();
    private Long userId;

    public JwtResponse(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }



}
