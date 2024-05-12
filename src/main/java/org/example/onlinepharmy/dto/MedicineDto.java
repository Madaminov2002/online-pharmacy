package org.example.onlinepharmy.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MedicineDto {
    private String medicineName;
    private LocalDateTime dateOfManufacture;
    private LocalDateTime dateOfExpiry;
    private String farm;

}
