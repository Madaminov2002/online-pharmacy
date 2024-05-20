package org.example.onlinepharmacy.updatedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableMedicineUpdateDto {
    private Long id;
    private Integer count;
    private Double price;
}
