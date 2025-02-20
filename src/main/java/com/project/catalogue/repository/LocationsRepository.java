package com.project.catalogue.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.project.catalogue.model.Locations;

public interface LocationsRepository extends JpaRepository<Locations, Long> {

    @Query("SELECT l.id, l.name, COUNT(a) FROM Locations l " +
           "LEFT JOIN Ads a ON a.location.id = l.id " +
           "WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "GROUP BY l.id, l.name")
    List<Object[]> searchLocations(@Param("keyword") String keyword);

    @Query("SELECT sl.id, sl.name, COUNT(a), sl.location.id FROM Sub_Location sl " +
           "LEFT JOIN Ads a ON a.subLocation.id = sl.id " +
           "WHERE LOWER(sl.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "GROUP BY sl.id, sl.name, sl.location.id")
    List<Object[]> searchSubLocations(@Param("keyword") String keyword);

    @Query("SELECT a.id, a.title, a.price FROM Ads a WHERE a.location.id = :locationId")
    List<Object[]> findAdsByLocationId(@Param("locationId") Long locationId);

    @Query("SELECT sl.id, sl.name, COUNT(a) FROM Sub_Location sl " +
           "LEFT JOIN Ads a ON a.subLocation.id = sl.id " +
           "WHERE sl.location.id = :locationId " +
           "GROUP BY sl.id, sl.name")
    List<Object[]> findSubLocationsByLocationId(@Param("locationId") Long locationId);
    
    // New method: fetch ads for a specific sublocation.
    @Query("SELECT a.id, a.title, a.price FROM Ads a WHERE a.subLocation.id = :subId")
    List<Object[]> findAdsBySubLocationId(@Param("subId") Long subId);
    @Query("SELECT COALESCE(COUNT(a), 0) FROM Ads a WHERE a.location.id = :locationId")
Long getAdsCountByLocationId(@Param("locationId") Long locationId);

}
