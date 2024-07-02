package org.example.onlinepharmacy.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.onlinepharmacy.domain.Basket;
import org.example.onlinepharmacy.domain.Medicine;
import org.example.onlinepharmacy.domain.User;
import org.example.onlinepharmacy.dto.BasketDto;
import org.example.onlinepharmacy.exception.BasketNotFoundException;
import org.example.onlinepharmacy.repo.BasketRepository;
import org.example.onlinepharmacy.repo.MedicineRepository;
import org.example.onlinepharmacy.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    public Basket dtoToEntity(BasketDto basketDto) {
        Optional<Medicine> medicine = medicineRepository.findById(basketDto.getMedicineId());
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return Basket.builder()
                .medicine(medicine.get())
                .user(user)
                .count(basketDto.getCount())
                .build();
    }

    public Basket save(BasketDto basketDto) {
        return basketRepository.save(dtoToEntity(basketDto));
    }

    public Basket update(BasketDto basketDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        Optional<Basket> basket = basketRepository.findBasketByUserIdAndMedicineId(user.getId(), basketDto.getMedicineId());
        if (basket.isEmpty()) {
            throw new BasketNotFoundException();
        }

        if (basketDto.getCount() != null) {
            basket.get().setCount(basketDto.getCount());
        }
        return basketRepository.save(basket.get());
    }


}
