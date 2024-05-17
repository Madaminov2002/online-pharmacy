package org.example.onlinePharmacy.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.dto.DistrictDto;
import org.example.onlinePharmacy.repo.DistrictRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public District dtoToEntity(DistrictDto districtDto) {
        return District.builder()
                .name(districtDto.getName())
                .build();
    }

    public District save(DistrictDto districtDto) {
        return districtRepository.save(dtoToEntity(districtDto));
    }
}
