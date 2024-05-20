package org.example.onlinePharmacy.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.advice.exception.AdminNotFoundException;
import org.example.onlinePharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.domain.Pharmacy;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.dto.PharmacyDto;
import org.example.onlinePharmacy.repo.DistrictRepository;
import org.example.onlinePharmacy.repo.PharmacyRepository;
import org.example.onlinePharmacy.repo.UserRepository;
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
