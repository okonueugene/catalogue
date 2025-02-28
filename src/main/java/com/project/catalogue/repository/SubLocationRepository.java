package com.project.catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catalogue.model.Sub_Location;

public interface SubLocationRepository extends JpaRepository<Sub_Location, Long> {
    List<Sub_Location> findByLocationId(Long locationId);
}


