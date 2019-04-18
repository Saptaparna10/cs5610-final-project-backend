package edu.northeastern.cs5610.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5610.models.*;

public interface RecipeListRepository extends CrudRepository<RecipeList, Integer>{

	@Query("SELECT p FROM RecipeList p WHERE p.name=:name")
	Iterable<RecipeList> findRecipeListByName(@Param("name") String name);
	
	@Query("SELECT p FROM RecipeList p WHERE p.moderator=:mod")
	Iterable<RecipeList> findRecipeListByModerator(@Param("mod") Moderator mod);
	
	@Query("SELECT p FROM RecipeList p WHERE p.moderator=:mod and p.name=:name")
	Iterable<RecipeList> findRecipeListByModeratorAndName(@Param("mod") Moderator mod, @Param("name") String name);
}
