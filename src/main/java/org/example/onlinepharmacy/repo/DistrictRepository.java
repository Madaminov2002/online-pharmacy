package org.example.onlinepharmacy.repo;

import java.util.Optional;
import org.example.onlinepharmacy.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findDistrictByName(String districtName);
}