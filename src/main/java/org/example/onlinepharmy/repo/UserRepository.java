package org.example.onlinepharmy.repo;

import org.example.onlinepharmy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}