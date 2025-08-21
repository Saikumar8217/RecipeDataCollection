package com.Recipe.service;



import com.Recipe.entity.Recipe;
import com.Recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Page<Recipe> getAllRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recipeRepository.findAllByOrderByRatingDesc(pageable);
    }

    public Page<Recipe> searchRecipes(String title, String cuisine, Float minRating, 
                                     Float maxRating, Integer minTotalTime, 
                                     Integer maxTotalTime, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return recipeRepository.searchRecipes(
            title, cuisine, minRating, maxRating, 
            minTotalTime, maxTotalTime, pageable
        );
    }
}