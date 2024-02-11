package com.axara.backend.repositories;

import com.axara.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT r FROM User r WHERE r.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}