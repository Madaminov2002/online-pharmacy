package org.example.onlinepharmy.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sales_history")
public class SalesHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private String checkPassword;

    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    private User user;

}
