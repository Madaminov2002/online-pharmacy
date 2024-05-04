package org.example.onlinepharmy.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "available_medicines")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableMedicines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Pharmacy pharmacy;
    private Integer count;
    @ManyToOne
    private Medicine medicine;
    private Double price;

}