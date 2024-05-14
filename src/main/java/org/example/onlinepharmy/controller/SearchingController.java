package org.example.onlinepharmy.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.AvailableMedicines;
import org.example.onlinepharmy.dto.SearchingDto;
import org.example.onlinepharmy.service.AvailableMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/searching")
@RequiredArgsConstructor
public class SearchingController {
    private final AvailableMedicineService availableMedicineService;

    @PostMapping
    public ResponseEntity<AvailableMedicines> searching(@RequestBody SearchingDto searchingDto) {
        return ResponseEntity.ok(availableMedicineService.getAvailableMedicineForSearching(searchingDto));
    }
}
