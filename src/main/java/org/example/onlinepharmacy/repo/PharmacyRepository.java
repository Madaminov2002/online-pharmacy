package org.example.onlinepharmacy.repo;

import java.util.Optional;
import org.example.onlinepharmacy.domain.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    @Query(nativeQuery = true, value = "select * from pharmacy where id=:pId and admin_id=:aId")
    Optional<Pharmacy> checkPharmacy(@Param("pId") Long pId, @Param("aId") Long aId);
}