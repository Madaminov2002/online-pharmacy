package org.example.onlinePharmacy.service;

import java.util.Optional;
import org.example.onlinePharmacy.advice.exception.AvailableMedicineNotFoundException;
import org.example.onlinePharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinePharmacy.advice.exception.MedicineNotFoundException;
import org.example.onlinePharmacy.advice.exception.MedicineNotFoundFromAvailableException;
import org.example.onlinePharmacy.domain.AvailableMedicines;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.domain.Medicine;
import org.example.onlinePharmacy.dto.AvailableMedicineDto;
import org.example.onlinePharmacy.dto.SearchingDto;
import org.example.onlinePharmacy.repo.AvailableMedicinesRepository;
import org.example.onlinePharmacy.repo.DistrictRepository;
import org.example.onlinePharmacy.repo.MedicineRepository;
import org.example.onlinePharmacy.updateDto.AvailableMedicineUpdateDto;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class AvailableMedicineServiceTest {
    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private AvailableMedicinesRepository availableMedicinesRepository;

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private AvailableMedicineService availableMedicineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDtoToEntityForException() {
        AvailableMedicineDto build = AvailableMedicineDto.builder()
                .medicineId(1L)
                .count(2)
                .price(12.00)
                .pharmacyId(2L)
                .build();

        when(medicineRepository.findById(build.getMedicineId())).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> availableMedicineService.dtoToEntity(build));

    }

    @Test
    void testMethodUpdateForException() {
        AvailableMedicineUpdateDto medicineUpdateDto = AvailableMedicineUpdateDto.builder()
                .id(1L)
                .count(2)
                .price(12.00)
                .build();
        when(availableMedicinesRepository.findById(medicineUpdateDto.getId())).thenReturn(Optional.empty());
        assertThrows(AvailableMedicineNotFoundException.class, () -> availableMedicineService.update(medicineUpdateDto));
    }

    @Test
    void testGetAvailableMedicineForSearchingForException1() {
        SearchingDto searchingDto = SearchingDto.builder()
                .medicineName("Trimol")
                .locationName("Buhoro")
                .build();
        when(districtRepository.findDistrictByName(searchingDto.getLocationName())).thenReturn(Optional.empty());

        assertThrows(DistrictNotFoundException.class, () -> availableMedicineService.getAvailableMedicineForSearching(searchingDto));
        verify(medicineRepository, never()).findMedicineByName(searchingDto.getMedicineName());
    }

    @Test
    void testGetAvailableMedicineForSearchingForException2() {
        SearchingDto searchingDto = SearchingDto.builder()
                .medicineName("Trimol")
                .locationName("Buhoro")
                .build();
        when(districtRepository.findDistrictByName(searchingDto.getLocationName())).thenReturn(Optional.of(new District()));
        when(medicineRepository.findMedicineByName(searchingDto.getMedicineName())).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundException.class, () -> availableMedicineService.getAvailableMedicineForSearching(searchingDto));
        verify(availableMedicinesRepository, never()).getAvailableMedicinesForSearching("Buhoro", 2L);
    }

    @Test
    void testGetAvailableMedicineForSearchingToOptionalDataForMethodGetAvailableMedicinesForSearching() {
        SearchingDto searchingDto = SearchingDto.builder()
                .medicineName("Trimol")
                .locationName("Buhoro")
                .build();
        District district = District.builder()
                .id(1L)
                .name("Chilonzor")
                .build();
        Medicine medicine = Medicine.builder()
                .id(1L)
                .name("Trimol")
                .build();
        when(districtRepository.findDistrictByName(searchingDto.getLocationName())).thenReturn(Optional.of(district));
        when(medicineRepository.findMedicineByName(searchingDto.getMedicineName())).thenReturn(Optional.of(medicine));
        when(availableMedicinesRepository.getAvailableMedicinesForSearching(district.getName(), medicine.getId())).thenReturn(Optional.of(new AvailableMedicines()));

        assertDoesNotThrow(() -> availableMedicineService.getAvailableMedicineForSearching(searchingDto));

        verify(availableMedicinesRepository, never()).getAvailableMedicinesByMedicineId(2L);
    }

    @Test
    void testGetAvailableMedicineForSearchingToOptionalDataForMethodGetAvailableMedicinesByMedicineId() {
        SearchingDto searchingDto = SearchingDto.builder()
                .medicineName("Trimol")
                .locationName("Buhoro")
                .build();
        District district = District.builder()
                .id(1L)
                .name("Chilonzor")
                .build();
        Medicine medicine = Medicine.builder()
                .id(1L)
                .name("Trimol")
                .build();
        when(districtRepository.findDistrictByName(searchingDto.getLocationName())).thenReturn(Optional.of(district));
        when(medicineRepository.findMedicineByName(searchingDto.getMedicineName())).thenReturn(Optional.of(medicine));
        when(availableMedicinesRepository.getAvailableMedicinesForSearching(district.getName(), medicine.getId())).thenReturn(Optional.empty());
        when(availableMedicinesRepository.getAvailableMedicinesByMedicineId(medicine.getId())).thenReturn(Optional.of(new AvailableMedicines()));

        assertDoesNotThrow(() -> availableMedicineService.getAvailableMedicineForSearching(searchingDto));

    }

    @Test
    void testGetAvailableMedicineForSearchingToError() {
        SearchingDto searchingDto = SearchingDto.builder()
                .medicineName("Trimol")
                .locationName("Buhoro")
                .build();
        District district = District.builder()
                .id(1L)
                .name("Chilonzor")
                .build();
        Medicine medicine = Medicine.builder()
                .id(1L)
                .name("Trimol")
                .build();
        when(districtRepository.findDistrictByName(searchingDto.getLocationName())).thenReturn(Optional.of(district));
        when(medicineRepository.findMedicineByName(searchingDto.getMedicineName())).thenReturn(Optional.of(medicine));
        when(availableMedicinesRepository.getAvailableMedicinesForSearching(district.getName(), medicine.getId())).thenReturn(Optional.empty());
        when(availableMedicinesRepository.getAvailableMedicinesByMedicineId(medicine.getId())).thenReturn(Optional.empty());

        assertThrows(MedicineNotFoundFromAvailableException.class, () -> availableMedicineService.getAvailableMedicineForSearching(searchingDto));

    }

}