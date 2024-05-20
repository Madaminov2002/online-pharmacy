package org.example.onlinepharmacy.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.domain.District;
import org.example.onlinepharmacy.dto.DistrictDto;
import org.example.onlinepharmacy.repo.DistrictRepository;
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
