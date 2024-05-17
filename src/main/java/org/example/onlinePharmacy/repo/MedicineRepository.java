package org.example.onlinePharmacy.repo;

import java.util.Optional;
import org.example.onlinePharmacy.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findMedicineByName(String name);
}