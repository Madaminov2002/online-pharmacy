package org.example.onlinepharmacy.jwt;

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
    private String location;

    public JwtResponse(Long userId, String accessToken, String location) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.location = location;
    }


}
