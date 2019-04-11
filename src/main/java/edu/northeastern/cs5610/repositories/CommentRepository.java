package edu.northeastern.cs5610.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5610.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer>{

}
