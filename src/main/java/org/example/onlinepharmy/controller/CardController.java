package org.example.onlinepharmy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Card API",
        description = "Designed to work with payments"
)
public class CardController {
    private final CardService cardService;

    @PostMapping("/save")
    @Operation(summary = "Save Cards", description = "It is designed to store cards")
    public ResponseEntity<Card> save(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.save(cardDto));
    }

    @GetMapping("/showCard")
    @Operation(summary = "Show Cards", description = "It is designed to showing cards")
    public ResponseEntity<Card> showCard() {
        return ResponseEntity.ok(cardService.showCard());
    }

}
