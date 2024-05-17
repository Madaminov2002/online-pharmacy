package org.example.onlinePharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.domain.District;
import org.example.onlinePharmacy.dto.DistrictDto;
import org.example.onlinePharmacy.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/district")
@Tag(
        name = "District API",
        description = "Designed to manage the APIs of the districts"
)public class DistrictController {
    private final DistrictService districtService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Save districts", description = "Designed to saving districts only for SUPER ADMINS")
    public ResponseEntity<District> save(@RequestBody DistrictDto districtDto) {
        return ResponseEntity.ok(districtService.save(districtDto));
    }
}
