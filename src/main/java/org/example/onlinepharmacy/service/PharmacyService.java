package org.example.onlinepharmacy.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.advice.exception.AdminNotFoundException;
import org.example.onlinepharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinepharmacy.domain.District;
import org.example.onlinepharmacy.domain.Pharmacy;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.PharmacyDto;
import org.example.onlinepharmacy.repo.DistrictRepository;
import org.example.onlinepharmacy.repo.PharmacyRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;
    private final DistrictRepository districtRepository;
    private final UserRepository userRepository;

   public Pharmacy dtoToEntity(PharmacyDto pharmacyDto) {
        Optional<District> district = districtRepository.findById(pharmacyDto.getDistrictId());

        if (district.isEmpty()) {
            throw new DistrictNotFoundException(String.valueOf(pharmacyDto.getDistrictId()));
        }

        Optional<User> adminById = userRepository.findAdminById(pharmacyDto.getAdminId());

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
