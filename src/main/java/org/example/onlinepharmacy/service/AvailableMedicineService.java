package org.example.onlinepharmacy.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.advice.exception.AvailableMedicineNotFoundException;
import org.example.onlinepharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinepharmacy.advice.exception.MedicineNotFoundException;
import org.example.onlinepharmacy.advice.exception.MedicineNotFoundFromAvailableException;
import org.example.onlinepharmacy.advice.exception.PharmacyIsNotYoursException;
import org.example.onlinepharmacy.domain.AvailableMedicines;
import org.example.onlinepharmacy.domain.District;
import org.example.onlinepharmacy.domain.Medicine;
import org.example.onlinepharmacy.domain.Pharmacy;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.AvailableMedicineDto;
import org.example.onlinepharmacy.dto.SearchingDto;
import org.example.onlinepharmacy.repo.AvailableMedicinesRepository;
import org.example.onlinepharmacy.repo.DistrictRepository;
import org.example.onlinepharmacy.repo.MedicineRepository;
import org.example.onlinepharmacy.repo.PharmacyRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.example.onlinepharmacy.updatedto.AvailableMedicineUpdateDto;
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
