package com.Recipe.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "recipes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cuisine;
    private String title;
    private Float rating;
    
    @Column(name = "prep_time")
    private Integer prepTime;
    
    @Column(name = "cook_time")
    private Integer cookTime;
    
    @Column(name = "total_time")
    private Integer totalTime;
    
    @Column(length = 1000)
    private String description;
    
    @Column(columnDefinition = "json")
    private String nutrients;
    
    private String serves;
    
    
 // Constructors, getters, and setters
    // ...
    public Recipe() {
    	
    }

	public Recipe(Long id, String cuisine, String title, Float rating, Integer prepTime, Integer cookTime,
			Integer totalTime, String description, String nutrients, String serves) {
		super();
		this.id = id;
		this.cuisine = cuisine;
		this.title = title;
		this.rating = rating;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.totalTime = totalTime;
		this.description = description;
		this.nutrients = nutrients;
		this.serves = serves;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCuisine() {
		return cuisine;
	}


	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Float getRating() {
		return rating;
	}


	public void setRating(Float rating) {
		this.rating = rating;
	}


	public Integer getPrepTime() {
		return prepTime;
	}


	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}


	public Integer getCookTime() {
		return cookTime;
	}


	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}


	public Integer getTotalTime() {
		return totalTime;
	}


	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNutrients() {
		return nutrients;
	}


	public void setNutrients(String nutrients) {
		this.nutrients = nutrients;
	}


	public String getServes() {
		return serves;
	}


	public void setServes(String serves) {
		this.serves = serves;
	}
	
	
	

    
    
}