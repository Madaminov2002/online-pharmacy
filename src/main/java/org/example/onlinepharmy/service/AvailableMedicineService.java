package org.example.onlinepharmy.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.advice.exception.AvailableMedicineNotFoundException;
import org.example.onlinepharmy.advice.exception.MedicineNotFoundException;
import org.example.onlinepharmy.advice.exception.PharmacyIsNotYoursException;
import org.example.onlinepharmy.domain.AvailableMedicines;
import org.example.onlinepharmy.domain.Medicine;
import org.example.onlinepharmy.domain.Pharmacy;
import org.example.onlinepharmy.domain.User;
import org.example.onlinepharmy.dto.AvailableMedicineDto;
import org.example.onlinepharmy.repo.AvailableMedicinesRepository;
import org.example.onlinepharmy.repo.MedicineRepository;
import org.example.onlinepharmy.repo.PharmacyRepository;
import org.example.onlinepharmy.repo.UserRepository;
import org.example.onlinepharmy.updateDto.AvailableMedicineUpdateDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailableMedicineService {
    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AvailableMedicinesRepository availableMedicinesRepository;
    private final UserRepository userRepository;

    public AvailableMedicines dtoToEntity(final AvailableMedicineDto dto) {
        Optional<Medicine> medicine = medicineRepository.findById(dto.getMedicineId());
        if (medicine.isEmpty()) {
            throw new MedicineNotFoundException(dto.getMedicineId());
        }
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(dto.getPharmacyId());
        return AvailableMedicines
                .builder()
                .count(dto.getCount())
                .price(dto.getPrice())
                .medicine(medicine.get())
                .pharmacy(pharmacy.get())
                .build();
    }

    public AvailableMedicines save(final AvailableMedicineDto dto) {
        if (checkAdminForError(dto.getPharmacyId())) {
            throw new PharmacyIsNotYoursException();
        }
        return availableMedicinesRepository.save(dtoToEntity(dto));
    }

    private Boolean checkAdminForError(Long pharmacyId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Optional<Pharmacy> pharmacy = pharmacyRepository.checkPharmacy(pharmacyId, user.getId());
        if (pharmacy.isEmpty()) {
            return true;
        }
        return false;

    }

    public List<AvailableMedicines> findAllAvailableMedicinesByDistrictName(String districtName) {
        return availableMedicinesRepository.findAvailableMedicinesByDistrictName(districtName);
    }

    public AvailableMedicines update(final AvailableMedicineUpdateDto updateDto) {
        AvailableMedicines availableMedicines = availableMedicinesRepository.findById(updateDto.getId()).orElse(null);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (availableMedicines == null) {
            throw new AvailableMedicineNotFoundException(updateDto.getId());
        }
        if (!Objects.equals(availableMedicines.getPharmacy().getAdmin().getId(), userRepository.findByEmail(email).getId())) {
            throw new PharmacyIsNotYoursException();
        }
        if (updateDto.getCount() != null) {
            availableMedicines.setCount(updateDto.getCount());
        }
        if (updateDto.getPrice() != null) {
            availableMedicines.setPrice(updateDto.getPrice());
        }
        return availableMedicinesRepository.save(availableMedicines);


    }

}
