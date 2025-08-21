package com.Recipe.repository;




import com.Recipe.entity.Recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAllByOrderByRatingDesc(Pageable pageable);
    
    @Query("SELECT r FROM Recipe r WHERE " +
           "(:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:cuisine IS NULL OR LOWER(r.cuisine) = LOWER(:cuisine)) AND " +
           "(:minRating IS NULL OR r.rating >= :minRating) AND " +
           "(:maxRating IS NULL OR r.rating <= :maxRating) AND " +
           "(:minTotalTime IS NULL OR r.totalTime >= :minTotalTime) AND " +
           "(:maxTotalTime IS NULL OR r.totalTime <= :maxTotalTime)")
    Page<Recipe> searchRecipes(
            @Param("title") String title,
            @Param("cuisine") String cuisine,
            @Param("minRating") Float minRating,
            @Param("maxRating") Float maxRating,
            @Param("minTotalTime") Integer minTotalTime,
            @Param("maxTotalTime") Integer maxTotalTime,
            Pageable pageable);
}