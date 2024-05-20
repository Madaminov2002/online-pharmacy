package org.example.onlinepharmacy.service;

import org.example.onlinepharmacy.domain.District;
import org.example.onlinepharmacy.dto.DistrictDto;
import org.example.onlinepharmacy.repo.DistrictRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class DistrictServiceTest {

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private DistrictService districtService;

    private DistrictDto districtDto;
    private District district;

    @BeforeEach
    void setUp() {
        districtDto = DistrictDto.builder()
                .name("Test District")
                .build();

        district = District.builder()
                .name("Test District")
                .build();
        district.setId(1L);
    }

    @Test
    void dtoToEntity() {
        District result = districtService.dtoToEntity(districtDto);

        assertNotNull(result, "Result should not be null");
        assertEquals(districtDto.getName(), result.getName(), "District name should match the DTO name");
    }

    @Test
    public void save() {
        when(districtRepository.save(any(District.class))).thenAnswer(invocation -> {
            District savedDistrict = invocation.getArgument(0);
            savedDistrict.setId(1L);
            return savedDistrict;
        });

        DistrictDto districtDto = DistrictDto.builder()
                .name("Sample District")
                .build();

        District savedDistrict = districtService.save(districtDto);

        assertNotNull(savedDistrict, "Saved district should not be null");
        assertNotNull(savedDistrict.getId(), "Saved district ID should not be null");
        assertEquals("Sample District", savedDistrict.getName());
    }

    @Test
    void findDistrictByName() {
        String districtName = "Test District";
        when(districtRepository.findDistrictByName(districtName)).thenReturn(Optional.of(district));

        Optional<District> result = districtService.findDistrictByName(districtName);

        assertTrue(result.isPresent(), "Result should be present");
        assertEquals(district, result.get(), "Found district should match the expected district");
        verify(districtRepository, times(1)).findDistrictByName(districtName);
    }
}
