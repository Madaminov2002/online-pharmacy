package org.example.onlinepharmy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PharmacyDto {
    private Long districtId;
    private Long adminId;
}