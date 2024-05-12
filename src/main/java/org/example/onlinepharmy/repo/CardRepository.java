package org.example.onlinepharmy.repo;

import org.example.onlinepharmy.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query(nativeQuery = true, value = "select * from card where card.user_id=:id")
    Card findCardByUserId(@Param("id") Long userId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update card set sum=card.sum-:price where user_id=:uId")
    void updateCardSumByUserId(@Param("uId") Long userId, @Param("price") Double price);
}