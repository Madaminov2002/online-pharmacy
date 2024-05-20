package org.example.onlinePharmacy.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.advice.exception.AvailableMedicineNotFoundException;
import org.example.onlinePharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinePharmacy.advice.exception.MedicineNotFoundException;
import org.example.onlinePharmacy.advice.exception.MedicineNotFoundFromAvailableException;
import org.example.onlinePharmacy.advice.exception.PharmacyIsNotYoursException;
import org.example.onlinePharmacy.domain.AvailableMedicines;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.domain.Medicine;
import org.example.onlinePharmacy.domain.Pharmacy;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.dto.AvailableMedicineDto;
import org.example.onlinePharmacy.dto.SearchingDto;
import org.example.onlinePharmacy.repo.AvailableMedicinesRepository;
import org.example.onlinePharmacy.repo.DistrictRepository;
import org.example.onlinePharmacy.repo.MedicineRepository;
import org.example.onlinePharmacy.repo.PharmacyRepository;
import org.example.onlinePharmacy.repo.UserRepository;
import org.example.onlinePharmacy.updateDto.AvailableMedicineUpdateDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailableMedicineService {
    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AvailableMedicinesRepository availableMedicinesRepository;
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;

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
        Optional<AvailableMedicines> availableMedicines = availableMedicinesRepository.findById(updateDto.getId());
        if (availableMedicines.isEmpty()) {
            throw new AvailableMedicineNotFoundException(updateDto.getId());
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!Objects.equals(availableMedicines.get().getPharmacy().getAdmin().getId(), userRepository.findByEmail(email).getId())) {
            throw new PharmacyIsNotYoursException();
        }
        if (updateDto.getCount() != null) {
            availableMedicines.get().setCount(updateDto.getCount());
        }
        if (updateDto.getPrice() != null) {
            availableMedicines.get().setPrice(updateDto.getPrice());
        }
        return availableMedicinesRepository.save(availableMedicines.get());
    }

    public AvailableMedicines getAvailableMedicineForSearching(final SearchingDto searchingDto) {
        Optional<District> district = districtRepository.findDistrictByName(searchingDto.getLocationName());

        if (district.isEmpty()) {
            throw new DistrictNotFoundException(searchingDto.getLocationName());
        }

        Optional<Medicine> medicine = medicineRepository.findMedicineByName(searchingDto.getMedicineName());

        if (medicine.isEmpty()) {
            throw new MedicineNotFoundException(0L);
        }
        var availableMedicinesForSearching = availableMedicinesRepository.getAvailableMedicinesForSearching(
                district.get().getName(),
                medicine.get().getId()
        );
        if (availableMedicinesForSearching.isPresent()) {
            return availableMedicinesForSearching.get();
        }
        var availableMedicinesByMedicineId = availableMedicinesRepository.getAvailableMedicinesByMedicineId(medicine.get().getId());

        if (availableMedicinesByMedicineId.isPresent()) {
            return availableMedicinesByMedicineId.get();
        }
        throw new MedicineNotFoundFromAvailableException(medicine.get().getId());
    }


}
