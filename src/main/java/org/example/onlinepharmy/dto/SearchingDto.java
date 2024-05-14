package org.example.onlinepharmy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchingDto {
    private String medicineName;
    private String locationName;
}
