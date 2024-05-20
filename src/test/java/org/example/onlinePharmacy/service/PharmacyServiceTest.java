package org.example.onlinePharmacy.service;

import java.util.Optional;
import org.example.onlinePharmacy.advice.exception.AdminNotFoundException;
import org.example.onlinePharmacy.advice.exception.DistrictNotFoundException;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.dto.PharmacyDto;
import org.example.onlinePharmacy.repo.DistrictRepository;
import org.example.onlinePharmacy.repo.PharmacyRepository;
import org.example.onlinePharmacy.repo.UserRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class PharmacyServiceTest {
    @Mock
    private PharmacyRepository pharmacyRepository;
    @Mock
    private DistrictRepository districtRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PharmacyService pharmacyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDtoToEntityMethodFindByIdForException() {
        PharmacyDto dto = PharmacyDto.builder()
                .districtId(1L)
                .build();
        when(districtRepository.findById(dto.getDistrictId())).thenReturn(Optional.empty());
        assertThrows(DistrictNotFoundException.class, () -> pharmacyService.dtoToEntity(dto));
        verify(userRepository, never()).findAdminById(2L);
    }

    @Test
    void testDtoToEntityMethodFindAdminByIdForException() {
        PharmacyDto dto = PharmacyDto.builder()
                .districtId(1L)
                .adminId(2L)
                .build();
        when(districtRepository.findById(dto.getDistrictId())).thenReturn(Optional.of(new District()));
        when(userRepository.findAdminById(dto.getAdminId())).thenReturn(Optional.empty());
        assertThrows(AdminNotFoundException.class, () -> pharmacyService.dtoToEntity(dto));
    }
}