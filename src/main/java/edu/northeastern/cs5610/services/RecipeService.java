package edu.northeastern.cs5610.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5610.models.*;
import edu.northeastern.cs5610.repositories.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class RecipeService {
	@Autowired
	RecipeRepository repository;
	
	@PostMapping("/api/recipe")
	public Recipe register(@RequestBody Recipe recipe) {

		return repository.save(recipe);
	}
	
	@GetMapping("/api/recipe/{id}")
	public Recipe findRecipeById(@PathVariable("id") int id) {
		Optional<Recipe> recipe =  repository.findById(id);
		if(recipe != null) {
			return recipe.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/recipe")
	public List<Recipe> findAllRecipes(){
		return (List<Recipe>) repository.findAll();
	}
	
	@PutMapping("api/recipe/{id}")
	public Recipe updateSong(@PathVariable("id") int id, @RequestBody Recipe recipe) {
		Recipe prev = findRecipeById(id);
		prev.set(recipe);
		return repository.save(prev);
		
	}
	
	@GetMapping("api/recipe/name/{name}")
	public Recipe findSongByName(@PathVariable("name") String name) {
		List<Recipe> recipes = (List<Recipe>) repository.findRecipeByName(name);
		if(recipes != null && !recipes.isEmpty()) {
			return recipes.get(0);
		}
		return null;
	}
	
	@DeleteMapping("/api/recipe/{id}")
	public void deleteRecipe(@PathVariable("id") int id) {
		
		repository.deleteById(id);
		
	}
	
	
	@DeleteMapping("/api/recipe")
	public void deleteAllSongs() {
		List<Recipe> recipes = findAllRecipes();
		for(Recipe r: recipes) {
			deleteRecipe(r.getId());
		}
	}
	 
	
}
