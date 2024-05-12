package org.example.onlinepharmy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Medicine API",
        description = "Designed to manage of medicines"
)
public class MedicineController {
    private final MedicineService medicineService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Save Medicines", description = "Intended for the storage of medicines only for SUPER ADMINS")
    public ResponseEntity<Medicine> save(@RequestBody MedicineDto medicineDto) {
        return ResponseEntity.ok(medicineService.save(medicineDto));
    }

    @GetMapping("/showAllMedicines")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Show all medicines", description = "Designed to show all medications only for ADMINS")
    public List<Medicine> showAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    @Operation(summary = "Update Medicine", description = "Designed to update medicines only for SUPER ADMINS")
    public ResponseEntity<Medicine> update(@RequestBody MedicineUpdateDto updateDto) {
        return ResponseEntity.ok(medicineService.medicineUpdate(updateDto));
    }
}
