package com.project.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catalogue.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
