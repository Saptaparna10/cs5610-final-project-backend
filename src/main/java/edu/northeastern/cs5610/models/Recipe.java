package edu.northeastern.cs5610.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
//	private String content;
	private String images;
	
	private String sourceRecipeUrl;
	private String sourceDisplayName;
	private String totalTime;
	private String numberOfServings;
	
	@ElementCollection
	List<String> ingredientLines;
	

//	@ManyToOne
//	private RegisteredUser recipeUser;
	
	@OneToMany(mappedBy = "recipe",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonIgnore
	private List<Comment> commentsReceived;
	
	@OneToMany(mappedBy = "favrecipe",cascade=CascadeType.ALL,orphanRemoval=true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Favourite> madeFavourite;
	
	@ManyToMany(mappedBy = "recipes",fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RecipeList> lists;
	
//	@ManyToMany(mappedBy = "recipes")
//	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection
	private List<String> cuisines;
	
	
	public Recipe() {
		commentsReceived= new ArrayList<>();
		madeFavourite=  new ArrayList<>();
		cuisines=  new ArrayList<>();
		lists= new ArrayList<>();
		ingredientLines= new ArrayList<>();
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



	public String getImages() {
		return images;
	}



	public void setImages(String images) {
		this.images = images;
	}



	public String getSourceRecipeUrl() {
		return sourceRecipeUrl;
	}



	public void setSourceRecipeUrl(String sourceRecipeUrl) {
		this.sourceRecipeUrl = sourceRecipeUrl;
	}



	public String getSourceDisplayName() {
		return sourceDisplayName;
	}



	public void setSourceDisplayName(String sourceDisplayName) {
		this.sourceDisplayName = sourceDisplayName;
	}



	public String getTotalTime() {
		return totalTime;
	}



	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}



	public String getNumberOfServings() {
		return numberOfServings;
	}



	public void setNumberOfServings(String numberOfServings) {
		this.numberOfServings = numberOfServings;
	}



	public List<String> getIngredientLines() {
		return ingredientLines;
	}



	public void setIngredientLines(List<String> ingredientLines) {
		this.ingredientLines = ingredientLines;
	}



	public List<Comment> getCommentsReceived() {
		return commentsReceived;
	}



	public void setCommentsReceived(List<Comment> commentsReceived) {
		this.commentsReceived = commentsReceived;
	}



	public List<Favourite> getMadeFavourite() {
		return madeFavourite;
	}



	public void setMadeFavourite(List<Favourite> madeFavourite) {
		this.madeFavourite = madeFavourite;
	}



	public List<RecipeList> getLists() {
		return lists;
	}



	public void setLists(List<RecipeList> lists) {
		this.lists = lists;
	}



	public List<String> getCuisines() {
		return cuisines;
	}



	public void setCuisines(List<String> cuisines) {
		this.cuisines = cuisines;
	}



	public void set(Recipe recipe) {
		this.setName(recipe.getName() != null ? recipe.getName() : this.getName());
		this.setImages(recipe.getImages() != null ? recipe.getImages() : this.getImages());
		//this.setRecipeUser(recipe.getRecipeUser() != null ? recipe.getRecipeUser() : this.getRecipeUser());
		this.setSourceRecipeUrl(recipe.getSourceRecipeUrl() != null ? recipe.getSourceRecipeUrl() : this.getSourceRecipeUrl());
		this.setSourceDisplayName(recipe.getSourceDisplayName() != null ? recipe.getSourceDisplayName() : this.getSourceDisplayName());
		this.setTotalTime(recipe.getTotalTime() != null ? recipe.getTotalTime() : this.getTotalTime());
		this.setNumberOfServings(recipe.getNumberOfServings() != null ? recipe.getNumberOfServings() : this.getNumberOfServings());

		if (recipe.getCommentsReceived() != null) {
			if (this.getCommentsReceived() == null) {
				this.setCommentsReceived(recipe.getCommentsReceived());
			} else if (!recipe.getCommentsReceived().equals(this.getCommentsReceived())) {
				this.setCommentsReceived(recipe.getCommentsReceived());
			}
		}
		
		if (recipe.getMadeFavourite() != null) {
			if (this.getMadeFavourite() == null) {
				this.setMadeFavourite(recipe.getMadeFavourite());
			} else if (!recipe.getMadeFavourite().equals(this.getMadeFavourite())) {
				this.setMadeFavourite(recipe.getMadeFavourite());
			}
		}
		if (recipe.getCuisines()!= null) {
			if (this.getCuisines() == null) {
				this.setCuisines(recipe.getCuisines());
			} else if (!recipe.getCuisines().equals(this.getCuisines())) {
				this.setCuisines(recipe.getCuisines());
			}
		}
		if (recipe.getLists()!= null) {
			if (this.getLists() == null) {
				this.setLists(recipe.getLists());
			} else if (!recipe.getLists().equals(this.getLists())) {
				this.setLists(recipe.getLists());
			}
		}
		
		if (recipe.getIngredientLines()!= null) {
			if (this.getIngredientLines() == null) {
				this.setIngredientLines(recipe.getIngredientLines());
			} else if (!recipe.getIngredientLines().equals(this.getIngredientLines())) {
				this.setIngredientLines(recipe.getIngredientLines());
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Recipe) {
			Recipe recipe = (Recipe) obj;
			if (this.getId() == recipe.getId()) {
				return true;
			}
		}
		return false;
	}
}
