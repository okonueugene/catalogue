package com.project.catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.catalogue.model.Sub_Categories;

public interface SubCategoriesRepository extends JpaRepository<Sub_Categories, Long> {
        @Query("SELECT s FROM Sub_Categories s WHERE s.category.id = :categoryId")
    List<Sub_Categories> findByCategoryId(@Param("categoryId") Long categoryId);
}


