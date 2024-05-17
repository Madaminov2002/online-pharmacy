package org.example.onlinePharmacy.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinePharmacy.domain.Card;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.dto.CardDto;
import org.example.onlinePharmacy.repo.CardRepository;
import org.example.onlinePharmacy.repo.UserRepository;
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
