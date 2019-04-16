package edu.northeastern.cs5610.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5610.models.*;

public interface FavouriteRepository extends CrudRepository<Favourite, Integer>{
	
	@Query("SELECT r FROM Favourite r WHERE r.favrecipe=:recipe")
	Iterable<Favourite> findFavoritesForRecipe(@Param("recipe") Recipe recipe);
	
	@Query("SELECT r FROM Favourite r WHERE r.user=:user")
	Iterable<Favourite> findFavoritesByUser(@Param("user") RegisteredUser user);
	
	@Query("SELECT r FROM Favourite r WHERE r.user=:user AND r.favrecipe=:recipe")
	Iterable<Favourite> recipeService(@Param("user") RegisteredUser user, @Param("recipe") Recipe recipe);

}
