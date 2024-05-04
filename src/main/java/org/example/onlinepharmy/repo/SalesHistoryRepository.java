package org.example.onlinepharmy.repo;

import org.example.onlinepharmy.domain.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
}