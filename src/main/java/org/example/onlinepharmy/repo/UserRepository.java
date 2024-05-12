package org.example.onlinepharmy.repo;

import java.util.Optional;
import org.example.onlinepharmy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from  users join public.users_roles ur on users.id = ur.user_id where ur.roles_id=2 and users.id=:id")
    Optional<User> findAdminById(@Param("id") Long id);

}