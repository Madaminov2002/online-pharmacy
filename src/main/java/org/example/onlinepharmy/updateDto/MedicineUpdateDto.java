package org.example.onlinepharmy.updateDto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicineUpdateDto {
    private Long id;
    private String name;
    private String farm;
    private LocalDateTime dateOfManufacture;
    private LocalDateTime dateOfExpiry;
}