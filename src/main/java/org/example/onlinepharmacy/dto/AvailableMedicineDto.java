package org.example.onlinepharmacy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableMedicineDto {
    private Long pharmacyId;
    private Long medicineId;
    private Integer count;
    private Double price;
}
