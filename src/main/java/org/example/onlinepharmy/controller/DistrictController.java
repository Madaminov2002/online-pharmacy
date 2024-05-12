package org.example.onlinepharmy.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.District;
import org.example.onlinepharmy.dto.DistrictDto;
import org.example.onlinepharmy.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/district")
public class DistrictController {
    private final DistrictService districtService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<District> save(@RequestBody DistrictDto districtDto) {
        return ResponseEntity.ok(districtService.save(districtDto));
    }
}
