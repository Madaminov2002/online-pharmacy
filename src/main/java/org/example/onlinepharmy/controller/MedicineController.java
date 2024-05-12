package org.example.onlinepharmy.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.Medicine;
import org.example.onlinepharmy.dto.MedicineDto;
import org.example.onlinepharmy.updateDto.MedicineUpdateDto;
import org.example.onlinepharmy.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicine")
public class MedicineController {
    private final MedicineService medicineService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Medicine> save(@RequestBody MedicineDto medicineDto) {
        return ResponseEntity.ok(medicineService.save(medicineDto));
    }

    @GetMapping("/showAllMedicines")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Medicine> showAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Medicine> update(@RequestBody MedicineUpdateDto updateDto) {
        return ResponseEntity.ok(medicineService.medicineUpdate(updateDto));
    }
}
