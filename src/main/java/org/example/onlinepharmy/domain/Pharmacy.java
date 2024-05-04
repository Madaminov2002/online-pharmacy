package org.example.onlinepharmy.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "pharmacy")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private District district;
    @OneToOne
    private User admin;

}