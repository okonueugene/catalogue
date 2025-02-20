package com.project.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.catalogue.model.Search_History;

public interface SearchHistoryRepository extends JpaRepository<Search_History, Long> {

}
