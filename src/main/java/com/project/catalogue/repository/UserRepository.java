package com.project.catalogue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.catalogue.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    //find user by email or phone
 @Query("SELECT u FROM Users u WHERE u.email = :email OR u.phone = :phone")
    Optional<Users> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

}