package org.example.onlinePharmacy.repo;

import org.example.onlinePharmacy.domain.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
}