package org.example.onlinepharmacy.repo;

import java.util.Optional;
import org.example.onlinepharmacy.domain.Basket;
import org.example.onlinepharmacy.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query(nativeQuery = true, value = "select * from public.basket b where b.user_id=:userId and b.medicine_Id=:medicineId")
    Optional<Basket> findBasketByUserIdAndMedicineId(@Param("userId") Long userId, @Param("medicineId") Long medicineId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from basket where medicine_id=:mId")
    void deleteBasketByMedicineId(@Param("mId") Long medicineId);
}