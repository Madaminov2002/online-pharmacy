package org.example.onlinePharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.domain.Pharmacy;
import org.example.onlinePharmacy.dto.PharmacyDto;
import org.example.onlinePharmacy.service.PharmacyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
@Tag(
        name = "Pharmacy API",
        description = "Designed to manage of pharmacy"
)
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Save Pharmacy", description = "Designed to saving pharmacy")
    public ResponseEntity<Pharmacy> save(@RequestBody PharmacyDto pharmacyDto) {
        return ResponseEntity.ok(pharmacyService.save(pharmacyDto));
    }

}
