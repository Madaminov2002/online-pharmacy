package org.example.onlinepharmacy.service;

import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.exception.IsNotEnoughMoneyException;
import org.example.onlinepharmacy.exception.MedicineNotFoundException;
import org.example.onlinepharmacy.domain.Card;
import org.example.onlinepharmacy.domain.Medicine;
import org.example.onlinepharmacy.domain.SalesHistory;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.repo.AvailableMedicinesRepository;
import org.example.onlinepharmacy.repo.CardRepository;
import org.example.onlinepharmacy.repo.MedicineRepository;
import org.example.onlinepharmacy.repo.PharmacyRepository;
import org.example.onlinepharmacy.repo.SalesHistoryRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SalesHistoryService {
    private final SalesHistoryRepository salesHistoryRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final CardRepository cardRepository;
    private final AvailableMedicinesRepository availableMedicinesRepository;
    private final PharmacyRepository pharmacyRepository;

    public SalesHistory save(Long medicineId, Long pharmacyId, Integer count) {
        Integer password = new Random().nextInt(100000, 1000000);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Medicine medicine = checkBalance(medicineId, pharmacyId, count);
        User user = userRepository.findByEmail(email);
        return salesHistoryRepository.save(
                SalesHistory.builder()
                        .checkPassword(password.toString())
                        .user(user)
                        .medicine(medicine)
                        .pharmacy(pharmacyRepository.findById(pharmacyId).get())
                        .build()
        );
    }

    private Medicine checkBalance(Long medicineId, Long pharmacyId, Integer count) {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        if (medicine.isEmpty()) {
            throw new MedicineNotFoundException(medicineId);
        }
        Double price = availableMedicinesRepository.findPriceByMedicineId(medicineId, pharmacyId);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Card cardByUserId = cardRepository.findCardByUserId(userRepository.findByEmail(email).getId());

        if (cardByUserId.getSum() >= price * count) {
            updateCardSumAndCountMedicine(userRepository.findByEmail(email).getId(), price, medicineId, pharmacyId);
            return medicine.get();
        }
        throw new IsNotEnoughMoneyException();
    }

    private void updateCardSumAndCountMedicine(Long userId, Double price, Long medicineId, Long pharmacyId) {
        cardRepository.updateCardSumByUserId(userId, price);
        availableMedicinesRepository.updateCountByMedicineIdAndPharmacyID(medicineId, pharmacyId);
    }
}
