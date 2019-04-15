package edu.northeastern.cs5610.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5610.models.Favourite;
import edu.northeastern.cs5610.models.Recipe;
import edu.northeastern.cs5610.models.RegisteredUser;
import edu.northeastern.cs5610.repositories.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class FavouriteService {
	@Autowired
	FavouriteRepository repository;
	
	@Autowired
	RegisteredUserService regUserService;
	
	@Autowired
	RecipeService recipeService;
	
	@PostMapping("/api/favorite")
	public Favourite createFavourite(@RequestBody Favourite fav) {
		return repository.save(fav);
	}
	
	@GetMapping("/api/favorite/recipe/{id}")
	public Iterable<Favourite> findFavoritesForRecipe(@PathVariable("id") int id) {
		Recipe recipe= recipeService.findRecipeById(id);
		Iterable<Favourite> favs=  repository.findFavoritesForRecipe(recipe);
		if(favs != null) {
			return favs;
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/favorite/user/{id}")
	public Iterable<Favourite> findFavoritesByUser(@PathVariable("id") int id) {
		RegisteredUser user= regUserService.findRegisteredUserById(id);

		Iterable<Favourite>  favs=  repository.findFavoritesByUser(user);
		if(favs != null) {
			return favs;
		}
		else {
			return null;
		}
	}
}
