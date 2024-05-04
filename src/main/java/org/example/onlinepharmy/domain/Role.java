package org.example.onlinepharmy.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}