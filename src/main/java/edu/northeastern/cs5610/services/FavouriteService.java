package edu.northeastern.cs5610.services;

import java.util.ArrayList;
import java.util.List;
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
@CrossOrigin(origins = "https://prsalwayshungry.herokuapp.com", allowCredentials = "true", allowedHeaders = "*")
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
		
		if(user == null || recipe == null)
			return null;
		
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
		
		if(fav == null)
			return;
		
		fav.getUser().getFavourites().remove(fav);
		fav.getFavrecipe().getCommentsReceived().remove(fav);
		
		recipeService.updateRecipe(fav.getFavrecipe().getId(), fav.getFavrecipe());
		regUserService.updateRegisteredUser(fav.getUser().getId(),fav.getUser());

		repository.deleteById(id);
	}
	
	@GetMapping("/api/favorite/{id}")
	public Favourite findFavoritetById(@PathVariable("id") int id) {
		Optional<Favourite> opt = repository.findById(id);
		
		if(opt.isPresent())
			return opt.get();
		return null;
	}
	
	@GetMapping("/api/favorite/recipe/{id}")
	public Iterable<Favourite> findFavoritesForRecipe(@PathVariable("id") String id) {
		Recipe recipe= recipeService.findRecipeById(id);
		if(recipe == null)
			return null;
		Iterable<Favourite> favs=  repository.findFavoritesForRecipe(recipe);
		if(favs != null) {
			return favs;
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/favorite/user/{id}")
	public List<Recipe> findFavoritesByUser(@PathVariable("id") int id) {
		if(id==0)
			return null;
		RegisteredUser user= regUserService.findRegisteredUserById(id);
		
		if(user == null)
			return null;

		List<Favourite>  favs=  (List<Favourite>) repository.findFavoritesByUser(user);
		
		if(favs == null)
			return null;
		
		List<Recipe> recipes = new ArrayList<>();
		for(Favourite fav: favs) {
			recipes.add(fav.getFavrecipe());
		}
		return recipes;
	}
	
	@GetMapping("/api/favorite/user/{userId}/recipe/{recipeId}")
	public Iterable<Favourite> findFavoritesForRecipebyUser(@PathVariable("userId") int userId, @PathVariable("recipeId") String recipeId) {
		if(userId==0)
			return null;
		
		RegisteredUser user= regUserService.findRegisteredUserById(userId);
		Recipe recipe= recipeService.findRecipeById(recipeId);
		
		if(user == null || recipe == null)
			return null;
		
		Iterable<Favourite> favs=  repository.findFavoritesForRecipebyUser(user,recipe);
		if(favs != null) {
			return favs;
		}
		else {
			return null;
		}
	}
	
}
