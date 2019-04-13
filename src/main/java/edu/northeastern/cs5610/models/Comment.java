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

	public RegisteredUser getCommentingUser() {
		return commentingUser;
	}

	public void setCommentingUser(RegisteredUser commentingUser) {
		this.commentingUser = commentingUser;
	}
	
	
	public void set(Comment comment) {
		this.setContent(comment.getContent() != null ? comment.getContent() : this.getContent());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentingUser == null) ? 0 : commentingUser.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
		return result;
	}

	/**
	 * Overridden version of the equals method Two moderator are considered equal only
	 * if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Comment) {
			Comment comment = (Comment) obj;
			if (this.getId() == comment.getId()) {
				return true;
			}
		}
		return false;
	}

}
