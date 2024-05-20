package org.example.onlinepharmacy.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.domain.Card;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.CardDto;
import org.example.onlinepharmacy.repo.CardRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Card dtoToEntity(CardDto cardDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        return Card.builder()
                .number(cardDto.getCardNumber())
                .sum(cardDto.getSum())
                .user(user)
                .build();
    }

    public Card save(CardDto cardDto) {
        return cardRepository.save(dtoToEntity(cardDto));
    }

    public Card showCard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByEmail(email).getId();
        return cardRepository.findCardByUserId(userId);
    }
}
