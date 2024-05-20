package org.example.onlinepharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.domain.SalesHistory;
import org.example.onlinepharmacy.service.SalesHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salesHistory")
@Tag(
        name = "Sales History API",
        description = "Designed to showing information of sales history"
)
public class SalesHistoryController {
    private final SalesHistoryService salesHistoryService;

    /**
     * @param medicineId
     * @param pharmacyId
     * @param count      example:/save/medicineID/{mId}?count=2/pharmacyID/{pId}
     */
    @PostMapping("/save/medicineID/{mId}/pharmacyID/{pId}")
    @Operation(summary = "Save History", description = "Designed to saving sales history")
    public ResponseEntity<SalesHistory> save(
            @PathVariable("mId") Long medicineId, @PathVariable("pId") Long pharmacyId,
            @RequestParam("count") Integer count
    ) {
        return ResponseEntity.ok(salesHistoryService.save(medicineId, pharmacyId, count));
    }
}
