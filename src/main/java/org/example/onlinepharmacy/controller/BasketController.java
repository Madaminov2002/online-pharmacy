package org.example.onlinepharmacy.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.domain.Basket;
import org.example.onlinepharmacy.dto.BasketDto;
import org.example.onlinepharmacy.repo.BasketRepository;
import org.example.onlinepharmacy.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;
    private final BasketRepository basketRepository;

    @PostMapping("/add-to-basket")
    public ResponseEntity<Basket> addToBasket(@RequestBody BasketDto basketDto) {
        return ResponseEntity.ok(basketService.save(basketDto));
    }

    @PutMapping("/update-basket")
    public ResponseEntity<Basket> updateBasket(@RequestBody BasketDto basketDto) {
        return ResponseEntity.ok(basketService.update(basketDto));
    }

    @DeleteMapping("/delete-by-id/medicineId/{id}")
    public ResponseEntity<String> deleteBasketById(@PathVariable("id") Long id) {
        basketRepository.deleteBasketByMedicineId(id);
        return ResponseEntity.ok("Basket successfully deleted");
    }

    @DeleteMapping("/delete-basket/userId/{id}")
    public ResponseEntity<String> deleteBasket(@PathVariable("id")Long id) {
        basketRepository.deleteById(id);
        return ResponseEntity.ok("Basket successfully deleted");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Basket>> getAllBasket() {
        return ResponseEntity.ok(basketRepository.findAll());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(basketRepository.findById(id).get());
    }
}
