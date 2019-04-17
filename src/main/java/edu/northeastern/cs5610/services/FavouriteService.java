package edu.northeastern.cs5610.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5610.models.Comment;
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
	
	@PostMapping("/api/favorite/user/{userId}/recipe/{recipeId}")
	public Favourite createFavourite(@PathVariable("userId") int userId, @PathVariable("recipeId") String recipeId, @RequestBody Favourite fav) {
		RegisteredUser user = regUserService.findRegisteredUserById(userId);
		//User user = userService.findUserById(userId);
		Recipe recipe = recipeService.findRecipeById(recipeId);
		
		fav.setUser(user);
		fav.setFavrecipe(recipe);
		
		fav = repository.save(fav);
		recipe.getMadeFavourite().add(fav);
		
		recipeService.updateRecipe(recipeId, recipe);
		regUserService.updateRegisteredUser(userId, user);
		return fav;
	}
	
	@DeleteMapping("/api/favorite/{id}")
	public void deleteFavorite(@PathVariable("id") int id) {
		
		Favourite fav = findFavoritetById(id);
		fav.getUser().getFavourites().remove(fav);
		fav.getFavrecipe().getCommentsReceived().remove(fav);
		
		recipeService.updateRecipe(fav.getFavrecipe().getId(), fav.getFavrecipe());
		regUserService.updateRegisteredUser(fav.getUser().getId(),fav.getUser());

		repository.deleteById(id);
	}
	
	@GetMapping("/api/favorite/{id}")
	public Favourite findFavoritetById(@PathVariable("id") int id) {
		return repository.findById(id).get();
	}
	
	@GetMapping("/api/favorite/recipe/{id}")
	public Iterable<Favourite> findFavoritesForRecipe(@PathVariable("id") String id) {
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
	
	@GetMapping("/api/favorite/user/{userId}/recipe/{recipeId}")
	public Iterable<Favourite> findFavoritesForRecipebyUser(@PathVariable("userId") int userId, @PathVariable("recipeId") String recipeId) {
		
		RegisteredUser user= regUserService.findRegisteredUserById(userId);
		Recipe recipe= recipeService.findRecipeById(recipeId);
		Iterable<Favourite> favs=  repository.findFavoritesForRecipebyUser(user,recipe);
		if(favs != null) {
			return favs;
		}
		else {
			return null;
		}
	}
	
}
