package edu.northeastern.cs5610.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5610.models.Comment;
import edu.northeastern.cs5610.models.Recipe;
import edu.northeastern.cs5610.models.RegisteredUser;

public interface CommentRepository extends CrudRepository<Comment, Integer>{


	@Query("SELECT r FROM Comment r WHERE r.commentingUser=:user and r.recipe=:recipe")
	Iterable<Comment> findCommentsByUserForRecipe(@Param("user") RegisteredUser user, @Param("recipe") Recipe recipe);

	@Query("SELECT r FROM Comment r WHERE r.recipe=:recipe")
	Iterable<Comment> findAllCommentsForRecipe(@Param("recipe") Recipe recipe);
}
