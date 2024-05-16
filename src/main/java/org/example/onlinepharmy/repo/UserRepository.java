package org.example.onlinepharmy.repo;

import java.util.Optional;
import org.example.onlinepharmy.Projection.UserDtoProjection;
import org.example.onlinepharmy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query(nativeQuery = true, value = "select * from  users join public.users_roles ur on users.id = ur.user_id where ur.roles_id=2 and users.id=:id")
    Optional<User> findAdminById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update users set password=:password where email=:email")
    void changePassword(@Param("password") String password, @Param("email") String email);

    @Query(nativeQuery = true,value = "select users.username,users.password,users.email from users where email=:email")
    UserDtoProjection getChangedPasswordUser(@Param("email")String email);

}