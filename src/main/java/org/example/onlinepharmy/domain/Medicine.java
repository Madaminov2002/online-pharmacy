package org.example.onlinepharmy.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "medicine")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Builder.Default
    private LocalDateTime dateOfManufacture=LocalDateTime.now();
    @Builder.Default
    private LocalDateTime dateOfExpiry=LocalDateTime.now();
    private String farm;

}