package org.example.onlinepharmy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private User user;
    private String password;
    private LocalTime expiryTime;

}