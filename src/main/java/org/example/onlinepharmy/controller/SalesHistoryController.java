package org.example.onlinepharmy.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.SalesHistory;
import org.example.onlinepharmy.service.SalesHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salesHistory")
public class SalesHistoryController {
    private final SalesHistoryService salesHistoryService;

    @PostMapping("/save/medicineID/{mId}/pharmacyID/{pId}")
    public ResponseEntity<SalesHistory> save(@PathVariable("mId") Long medicineId, @PathVariable("pId") Long pharmacyId) {
        return ResponseEntity.ok(salesHistoryService.save(medicineId, pharmacyId));
    }
}
