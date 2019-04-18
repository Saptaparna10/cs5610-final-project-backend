package edu.northeastern.cs5610.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RecipeList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String imageURL;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Moderator moderator;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "recipeToList")
	@JsonIgnore
	private List<Recipe> recipes;
	
	
	public RecipeList() {
		recipes= new ArrayList<>();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Moderator getModerator() {
		return moderator;
	}

	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	


	public void addRecipeToRecipeList(Recipe recipe) {
		this.recipes.add(recipe);
		if (!recipe.getLists().contains(this)) {
			recipe.getLists().add(this);
		}		
	}


	public void removeRecipeFromRecipeList(Recipe recipe) {
		this.recipes.remove(recipe);
		if (recipe.getLists().contains(this)) {
			recipe.getLists().remove(this);
		}
		
	}


	public void set(RecipeList list) {

		this.setName(list.getName() != null ? list.getName() : this.getName());
		this.setImageURL(list.getImageURL() != null ? list.getImageURL() : this.getImageURL());
		this.setModerator(list.getModerator() != null ? list.getModerator() : this.getModerator());
		
//		if (list.getRecipes() != null) {
//			if (this.getRecipes() == null) {
//				this.setRecipes(list.getRecipes());
//			} else if (!this.getRecipes().equals(list.getRecipes())) {
//				this.setRecipes(list.getRecipes());
//			}
//		}
//		
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecipeList) {
			RecipeList recipe = (RecipeList) obj;
			if (this.getId() == recipe.getId()) {
				return true;
			}
		}
		return false;
	}
}
