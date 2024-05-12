package org.example.onlinepharmy.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmy.domain.Card;
import org.example.onlinepharmy.dto.CardDto;
import org.example.onlinepharmy.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    @PostMapping("/save")
    public ResponseEntity<Card> save(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.save(cardDto));
    }

    @GetMapping("/showCard")
    public ResponseEntity<Card> showCard() {
        return ResponseEntity.ok(cardService.showCard());
    }
}
