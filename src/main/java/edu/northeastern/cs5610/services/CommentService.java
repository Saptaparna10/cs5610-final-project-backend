package edu.northeastern.cs5610.services;

import java.util.List;

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
public class CommentService {
	
	@Autowired
	CommentRepository repository;
	
	@Autowired
	RegisteredUserService regUserService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RecipeService recipeService;
	
	@PostMapping("/api/comment/user/{userId}/recipe/{recipeId}")
	public Comment createComment(@PathVariable("userId") int userId, @PathVariable("recipeId") String recipeId, @RequestBody Comment comment) {
		RegisteredUser user = regUserService.findRegisteredUserById(userId);
		//User user = userService.findUserById(userId);
		Recipe recipe = recipeService.findRecipeById(recipeId);
		
		comment.setUser(user);
		comment.setRecipe(recipe);
		
		comment = repository.save(comment);
		recipe.getCommentsReceived().add(comment);
		
		recipeService.updateRecipe(recipeId, recipe);
		regUserService.updateRegisteredUser(userId, user);
		return comment;
	}
	
	@GetMapping("/api/comment")
	public List<Comment> findAllComments(){
		return (List<Comment>) repository.findAll();
	}
	
	@GetMapping("/api/comment/{id}")
	public Comment findCommentById(@PathVariable("id") int id) {
		
		return repository.findById(id).get();
	}
	
	@GetMapping("/api/comment/user/{userId}/recipe/{recipeId}")
	public List<Comment> findCommentsByUserForRecipe(@PathVariable("userId") int userId, @PathVariable("recipeId") String recipeId) {
		
		return (List<Comment>) repository.findCommentsByUserForRecipe(regUserService.findRegisteredUserById(userId), recipeService.findRecipeById(recipeId));
	}
	
	@GetMapping("/api/comment/recipe/{recipeId}")
	public List<Comment> findAllCommentsForRecipe(@PathVariable("recipeId") String recipeId){
		
		return (List<Comment>) repository.findAllCommentsForRecipe(recipeService.findRecipeById(recipeId));
	}
	
	@PutMapping("/api/comment/{id}")
	public Comment updateComment(@PathVariable("id") int id, @RequestBody Comment comment) {
		Comment prev = findCommentById(id);
		prev.set(comment);
		return repository.save(prev);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public void deleteComment(@PathVariable("id") int id) {
		
		Comment comment = findCommentById(id);
		comment.getCommentingUser().getComments().remove(comment);
		comment.getRecipe().getCommentsReceived().remove(comment);
		
		recipeService.updateRecipe(comment.getRecipe().getId(), comment.getRecipe());
		regUserService.updateRegisteredUser(comment.getCommentingUser().getId(),comment.getCommentingUser());

		repository.deleteById(id);
	}
	
	@DeleteMapping("/api/comment")
	public void deleteAllComments() {
		List<Comment> comments = findAllComments();
		for(Comment comment: comments) {
			deleteComment(comment.getId());
		}
	}
}
