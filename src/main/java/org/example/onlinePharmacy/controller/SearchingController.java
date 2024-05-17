package org.example.onlinePharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.domain.AvailableMedicines;
import org.example.onlinePharmacy.dto.SearchingDto;
import org.example.onlinePharmacy.service.AvailableMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searching")
@RequiredArgsConstructor
@Tag(
        name = "Searching Medicines API",
        description = "Designed to searching medicines"
)
public class SearchingController {
    private final AvailableMedicineService availableMedicineService;

    @PostMapping
    @Operation(summary = "Search Medicine", description = "Designed to search for medicines")
    public ResponseEntity<AvailableMedicines> searching(@RequestBody SearchingDto searchingDto) {
        return ResponseEntity.ok(availableMedicineService.getAvailableMedicineForSearching(searchingDto));
    }
}
