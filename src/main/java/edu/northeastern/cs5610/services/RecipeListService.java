package edu.northeastern.cs5610.services;

import java.util.List;
import java.util.Optional;

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
import edu.northeastern.cs5610.repositories.RecipeListRepository;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class RecipeListService {

	@Autowired
	private RecipeListRepository repository;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private ModeratorService modService;


	@PostMapping("/api/recipelist/{modId}")
	public RecipeList createRecipeList(@PathVariable("modId") int modId, @RequestBody RecipeList recipeList) {
		recipeList = repository.save(recipeList);

		Moderator owner = modService.findModeratorById(modId);

		if(owner == null)
			return null;

		if(owner != null) {
			recipeList.setModerator(owner);
			owner.getRecipeLists().add(recipeList);
			modService.updateModerator(owner.getId(), owner);
		}
		return recipeList;

	}

	@GetMapping("/api/recipelist/{id}")
	public RecipeList findRecipeListById(@PathVariable("id") int id) {
		Optional<RecipeList> recipeList =  repository.findById(id);
		if(recipeList != null) {
			return recipeList.get();
		}
		else {
			return null;
		}
	}

	@GetMapping("/api/recipelist")
	public List<RecipeList> findAllRecipeLists(){
		return (List<RecipeList>) repository.findAll();
	}

	@GetMapping("/api/moderator/{mid}/recipe/{rid}")
	public Boolean searchModeratorRecipeList(@PathVariable("mid") int moderatorId, @PathVariable("rid") String recipeId){
		List<RecipeList> recipeList = (List<RecipeList>)repository.findRecipeListByModerator(modService.findModeratorById(moderatorId));
		for(RecipeList rl : recipeList)
		{
			for (Recipe r : rl.getRecipes())
			{
				if(r.getId().equals(recipeId))
					return true;
			}
		}
		return false;
	}

	@GetMapping("/api/moderator/{mid}/recipelist")
	public List<RecipeList> searchRecipeListByModerator(@PathVariable("mid") int moderatorId){
		List<RecipeList> recipeList = (List<RecipeList>)repository.findRecipeListByModerator(modService.findModeratorById(moderatorId));
		return recipeList;
	}


	@PutMapping("/api/recipelist/{recipelistId}/recipe/{recipeId}")
	public RecipeList addRecipeToRecipeList(@PathVariable("recipelistId") int recipelistId, @PathVariable("recipeId") String recipeId) {

		RecipeList recipelist = findRecipeListById(recipelistId);
		Recipe recipe = recipeService.findRecipeById(recipeId);

		if(recipelist != null && recipe != null) {
			if(!recipelist.getRecipes().contains(recipe)) {
				recipelist.addRecipeToRecipeList(recipe);
				return repository.save(recipelist);
			}
		}
		System.out.println("Either recipeList or recipe is NULL");
		return null;

	}


	@DeleteMapping("/api/recipelist/{recipelistId}/recipe/{recipeId}")
	public RecipeList removeRecipeFromRecipeList(@PathVariable("recipelistId") int recipelistId, @PathVariable("recipeId") String recipeId) {

		RecipeList recipeList = findRecipeListById(recipelistId);
		Recipe recipe = recipeService.findRecipeById(recipeId);

		if(recipeList != null && recipe != null) {
			recipeList.removeRecipeFromRecipeList(recipe);
			return repository.save(recipeList);
		}
		System.out.println("Either recipeList or recipe is NULL");
		return null;
	}


	@PutMapping("/api/recipelist/{id}")
	public RecipeList updateRecipeList(@PathVariable("id") int id, @RequestBody RecipeList recipeList) {
		RecipeList prevlist = findRecipeListById(id);
		if(prevlist == null)
			return null;
		prevlist.set(recipeList);
		return repository.save(prevlist);
	}


	@GetMapping("/api/recipelist/name/{name}")
	public RecipeList findRecipListByName(@PathVariable("name") String name) {
		List<RecipeList> recipelists = (List<RecipeList>) repository.findRecipeListByName(name);
		if(recipelists != null && !recipelists.isEmpty()) {
			return recipelists.get(0);
		}
		return null;
	}


	@DeleteMapping("/api/recipelist/{id}")
	public void deleteRecipeList(@PathVariable ("id") int id) {
		RecipeList recipelist = findRecipeListById(id);
		List<Recipe> recipes = recipelist.getRecipes();
		if(recipes != null && !recipes.isEmpty()) {
			for(Recipe recipe: recipes) {
				recipe.getLists().remove(recipelist);
				recipeService.updateRecipe(recipe.getId(), recipe);
			}
		}
		Moderator owner = recipelist.getModerator();
		if(owner != null) {
			owner.getRecipeLists().remove(recipelist);
			modService.updateModerator(owner.getId(), owner);
		}

		repository.deleteById(id);
	}


	@DeleteMapping("/api/recipelist")
	public void deleteAllRecipeLists() {
		List<RecipeList> recipeLists = findAllRecipeLists();
		for(RecipeList recipelist: recipeLists) {
			deleteRecipeList(recipelist.getId());
		}
	}

	@GetMapping("/api/recipelist/{cid}/recipes")
	public List<Recipe> getRecipesByRecipeList(@PathVariable ("cid") int cid) {
		RecipeList list = findRecipeListById(cid);
		if(list == null)
			return null;

		return list.getRecipes();
	}
	
	@GetMapping("/api/recipelist/{cid}/moderator")
	public Moderator findModeratorForRecipeList(@PathVariable ("cid") int cid) {
		RecipeList list = findRecipeListById(cid);
		if(list == null)
			return null;
		
		return list.getModerator();
	}
}
