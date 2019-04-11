package edu.northeastern.cs5610.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class RegisteredUser extends User {
	
	@OneToMany(mappedBy = "commentingUser")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "user")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Favourite> favourites;
	
	@OneToMany(mappedBy = "recipeUser")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Recipe> recipes;
	
	

	public List<Recipe> getRecipes() {
		return recipes;
	}


	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}


	public RegisteredUser() {
		super();
		comments= new ArrayList<>();
		favourites= new ArrayList<>();
		recipes= new ArrayList<>();
	}
	
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public List<Favourite> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Favourite> favourites) {
		this.favourites = favourites;
	}

	public void set(RegisteredUser user){
		super.set(user);
		if (user.getComments() != null) {
			if (this.getComments() == null) {
				this.setComments(user.getComments());
			} else if (!this.getComments().equals(user.getComments())) {
				this.setComments(user.getComments());
			}
		}
		if (user.getFavourites() != null) {
			if (this.getFavourites() == null) {
				this.setFavourites(user.getFavourites());
			} else if (!this.getFavourites().equals(user.getFavourites())) {
				this.setFavourites(user.getFavourites());
			}
		}
		
		if (user.getRecipes() != null) {
			if (this.getRecipes() == null) {
				this.setRecipes(user.getRecipes());
			} else if (!this.getRecipes().equals(user.getRecipes())) {
				this.setRecipes(user.getRecipes());
			}
		}
	}
}