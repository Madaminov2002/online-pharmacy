package org.example.onlinepharmacy.repo;

import org.example.onlinepharmacy.domain.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
}