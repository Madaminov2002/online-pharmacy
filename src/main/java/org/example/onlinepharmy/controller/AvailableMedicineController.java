package org.example.onlinepharmy.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.AvailableMedicines;
import org.example.onlinepharmy.dto.AvailableMedicineDto;
import org.example.onlinepharmy.service.AvailableMedicineService;
import org.example.onlinepharmy.updateDto.AvailableMedicineUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availableMedicine")
@RequiredArgsConstructor
public class AvailableMedicineController {
    private final AvailableMedicineService availableMedicineService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableMedicines> save(@RequestBody AvailableMedicineDto availableMedicineDto) {
        return ResponseEntity.ok(availableMedicineService.save(availableMedicineDto));
    }

    @GetMapping("/showAll/districtName/{name}")
    public ResponseEntity<List<AvailableMedicines>> showAll(@PathVariable("name") String districtName) {
        return ResponseEntity.ok(availableMedicineService.findAllAvailableMedicinesByDistrictName(districtName));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableMedicines> update(@RequestBody AvailableMedicineUpdateDto updateDto) {
        return ResponseEntity.ok(availableMedicineService.update(updateDto));
    }
}
