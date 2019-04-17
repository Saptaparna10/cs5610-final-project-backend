package edu.northeastern.cs5610.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5610.models.*;

public interface RecipeRepository extends CrudRepository<Recipe, String>{

	@Query("SELECT s FROM Recipe s WHERE s.name=:name")
	Iterable<Recipe> findRecipeByName(@Param("name") String name);

}
