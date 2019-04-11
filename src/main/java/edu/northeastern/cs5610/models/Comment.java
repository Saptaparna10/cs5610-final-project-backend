package edu.northeastern.cs5610.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	private String content;


	@ManyToOne
	private RegisteredUser commentingUser;
	
	@ManyToOne
	private Recipe recipe;	
	
	
	
	public Comment() {
		
	}
	
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return commentingUser;
	}

	public void setUser(RegisteredUser user) {
		this.commentingUser = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void set(Comment comment) {
		this.setContent(comment.getContent() != null ? comment.getContent() : this.getContent());
	}
	
}
