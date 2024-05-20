package org.example.onlinePharmacy.service;

import java.util.Optional;
import org.example.onlinePharmacy.domain.Card;
import org.example.onlinePharmacy.domain.Medicine;
import org.example.onlinePharmacy.domain.Pharmacy;
import org.example.onlinePharmacy.domain.SalesHistory;
import org.example.onlinePharmacy.domain.User;
import org.example.onlinePharmacy.repo.AvailableMedicinesRepository;
import org.example.onlinePharmacy.repo.CardRepository;
import org.example.onlinePharmacy.repo.MedicineRepository;
import org.example.onlinePharmacy.repo.PharmacyRepository;
import org.example.onlinePharmacy.repo.SalesHistoryRepository;
import org.example.onlinePharmacy.repo.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class SalesHistoryServiceTest {

    @Mock
    private SalesHistoryRepository salesHistoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MedicineRepository medicineRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private AvailableMedicinesRepository availableMedicinesRepository;
    @Mock
    private PharmacyRepository pharmacyRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private SalesHistoryService salesHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testSave() {
        Long medicineId = 1L;
        Long pharmacyId = 1L;
        Integer count = 10;

        String email = "test@gmail.com";

        Medicine medicine = new Medicine();
        medicine.setId(medicineId);
        medicine.setName("medicineTest");

        User user = new User();
        user.setEmail(email);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(pharmacyId);

        Card card = new Card();
        card.setSum(1000.0);

        when(authentication.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(pharmacy));
        when(cardRepository.findCardByUserId(user.getId())).thenReturn(card);

        SalesHistory salesHistory = SalesHistory.builder()
                .checkPassword("test")
                .user(user)
                .medicine(medicine)
                .pharmacy(pharmacy)
                .build();

        when(salesHistoryRepository.save(any(SalesHistory.class))).thenReturn(salesHistory);

        SalesHistory result = salesHistoryService.save(medicineId, pharmacyId, count);

        assertNotNull(result);
        assertEquals(email, result.getUser().getEmail());
        assertEquals(medicineId, result.getMedicine().getId());
        assertEquals(pharmacyId, result.getPharmacy().getId());
        assertNotNull(result.getCheckPassword());

        verify(salesHistoryRepository, times(1)).save(any(SalesHistory.class));
    }
}
