package com.project.catalogue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.catalogue.model.Sub_Location;
import com.project.catalogue.repository.SubLocationRepository;

@Service
public class SubLocationService {
  @Autowired
    private SubLocationRepository subLocationRepository;

    public List<Sub_Location> findByLocationId(Long locationId) {
        return subLocationRepository.findByLocationId(locationId);
    }
}
