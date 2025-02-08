package com.project.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catalogue.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
