package com.project.catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.catalogue.model.Ads;

public interface AdsRepository extends JpaRepository<Ads, Long> {
    @Query("SELECT c.id, c.name, COUNT(a) " +
       "FROM Ads a JOIN a.category c " +
       "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "   OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "GROUP BY c.id, c.name")
List<Object[]> searchAdsByKeywordGroupByCategory(@Param("keyword") String keyword);
@Query("SELECT a FROM Ads a LEFT JOIN FETCH a.images")
List<Ads> findAllWithImages();

//find by current user_id
@Query("SELECT a FROM Ads a WHERE a.user.id = :userId")
List<Ads> findByUserId(@Param("userId") Long userId);

//related ads
@Query("SELECT a FROM Ads a WHERE a.category.id = :categoryId " +
       "AND a.subCategory.id = :subCategoryId " +
       "AND a.id <> :adId")
List<Ads> findRelatedAds(@Param("categoryId") Long categoryId,
                         @Param("subCategoryId") Long subCategoryId,
                         @Param("adId") Long adId);


}


