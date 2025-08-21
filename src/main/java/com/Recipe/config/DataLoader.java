package com.Recipe.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Recipe.entity.Recipe;
import com.Recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {

        if (recipeRepository.count() > 0) {
            System.out.println(" Recipes already exist, skipping JSON import");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/US_recipes.json");

        if (inputStream == null) {
            System.err.println(" JSON file not found in resources!");
            return;
        }

        // Read JSON as string and replace NaN
        String jsonContent = new String(inputStream.readAllBytes());
        jsonContent = jsonContent.replace("NaN", "null");

        JsonNode rootNode = objectMapper.readTree(jsonContent);

        if (!rootNode.isObject()) {
            System.err.println(" JSON root is not an object with numeric keys");
            return;
        }

        int count = 0;
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            JsonNode recipeNode = entry.getValue();

            String title = getStringValue(recipeNode, "title");
            if (title == null || title.isBlank()) {
                System.out.println(" Skipping recipe with missing title at key: " + entry.getKey());
                continue; // skip this recipe
            }

            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setCuisine(getStringValue(recipeNode, "cuisine"));
            recipe.setRating(getFloatValue(recipeNode, "rating"));
            recipe.setPrepTime(getIntValue(recipeNode, "prep_time"));
            recipe.setCookTime(getIntValue(recipeNode, "cook_time"));
            recipe.setTotalTime(getIntValue(recipeNode, "total_time"));
            recipe.setDescription(getStringValue(recipeNode, "description"));
            recipe.setServes(getStringValue(recipeNode, "serves"));

            if (recipeNode.has("nutrients")) {
                recipe.setNutrients(recipeNode.get("nutrients").toString());
            }

            recipeRepository.save(recipe);
            count++;
        }

        System.out.println(" Inserted " + count + " recipes into DB");
    }

    private String getStringValue(JsonNode node, String fieldName) {
        return node.has(fieldName) && !node.get(fieldName).isNull() ? node.get(fieldName).asText() : null;
    }

    private Float getFloatValue(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            try {
                return Float.parseFloat(node.get(fieldName).asText());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Integer getIntValue(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            try {
                return Integer.parseInt(node.get(fieldName).asText());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
