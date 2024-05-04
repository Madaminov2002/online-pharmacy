package org.example.onlinepharmy.repo;

import org.example.onlinepharmy.domain.AvailableMedicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableMedicinesRepository extends JpaRepository<AvailableMedicines, Long> {
}