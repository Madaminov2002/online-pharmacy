package org.example.onlinepharmy.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.advice.exception.AdminNotFoundException;
import org.example.onlinepharmy.advice.exception.DistrictNotFoundException;
import org.example.onlinepharmy.domain.District;
import org.example.onlinepharmy.domain.Pharmacy;
import org.example.onlinepharmy.domain.User;
import org.example.onlinepharmy.dto.PharmacyDto;
import org.example.onlinepharmy.repo.DistrictRepository;
import org.example.onlinepharmy.repo.PharmacyRepository;
import org.example.onlinepharmy.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final DistrictRepository districtRepository;
    private final UserRepository userRepository;

    private Pharmacy dtoToEntity(PharmacyDto pharmacyDto) {
        Optional<District> district = districtRepository.findById(pharmacyDto.getDistrictId());
        Optional<User> adminById = userRepository.findAdminById(pharmacyDto.getAdminId());
        if (district.isEmpty()) {
            throw new DistrictNotFoundException(pharmacyDto.getDistrictId());
        }
        if (adminById.isEmpty()) {
            throw new AdminNotFoundException(pharmacyDto.getAdminId());
        }
        return Pharmacy.builder()
                .district(district.get())
                .admin(adminById.get())
                .build();
    }

    public Pharmacy save(PharmacyDto pharmacyDto) {
        return pharmacyRepository.save(dtoToEntity(pharmacyDto));
    }
}
