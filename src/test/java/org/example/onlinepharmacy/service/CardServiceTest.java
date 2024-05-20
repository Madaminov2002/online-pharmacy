package org.example.onlinepharmacy.service;

import org.example.onlinepharmacy.domain.Card;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.CardDto;
import org.example.onlinepharmacy.repo.CardRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardService cardService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private User user;
    private CardDto cardDto;
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        cardDto = CardDto.builder()
                .cardNumber("1234567890")
                .sum(1000.0)
                .build();

        card = Card.builder()
                .number("1234567890")
                .sum(1000.0)
                .user(user)
                .build();
    }

    @Test
    void dtoToEntity() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        Card result = cardService.dtoToEntity(cardDto);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getUser(), "Result user should not be null");
        assertEquals(user, result.getUser(), "User should match the mock user");
        assertEquals("1234567890", result.getNumber());
        assertEquals(1000.0, result.getSum());
    }

    @Test
    void save() {
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        Card result = cardService.save(cardDto);

        assertNotNull(result, "Saved card should not be null");
        assertEquals(card, result, "Saved card should match the expected card");
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void showCard() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(cardRepository.findCardByUserId(anyLong())).thenReturn(card);

        Card result = cardService.showCard();

        assertNotNull(result, "Returned card should not be null");
        assertEquals(card, result, "Returned card should match the expected card");
        verify(cardRepository, times(1)).findCardByUserId(anyLong());
    }

    @Test
    void updateCardSumByUserId() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(user);


        cardService.updateCardSumByUserId(1L, 100.0);
        verify(cardRepository, times(1)).updateCardSumByUserId(1L, 100.0);
    }
}

