package org.example.onlinepharmy.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.Pharmacy;
import org.example.onlinepharmy.dto.PharmacyDto;
import org.example.onlinepharmy.service.PharmacyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Pharmacy> save(@RequestBody PharmacyDto pharmacyDto) {
        return ResponseEntity.ok(pharmacyService.save(pharmacyDto));
    }
}
