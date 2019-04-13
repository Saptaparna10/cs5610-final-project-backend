package edu.northeastern.cs5610.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
	private String title;
	private String content;
	private String imageUrl;
	
	private String srcUrl;
	private String srcTitle;
	private String prepTime;
	private String servings;
	
	@ElementCollection
	List<String> ingredients;
	

//	@ManyToOne
//	private RegisteredUser recipeUser;
	
	@OneToMany(mappedBy = "recipe")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Comment> commentsReceived;
	
	@OneToMany(mappedBy = "favrecipe")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Favourite> madeFavourite;
	
	@ManyToMany(mappedBy = "recipes")
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
		ingredients= new ArrayList<>();
	}
	

	public List<RecipeList> getLists() {
		return lists;
	}

	public void setLists(List<RecipeList> lists) {
		this.lists = lists;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

//	public RegisteredUser getRecipeUser() {
//		return recipeUser;
//	}
//
//	public void setRecipeUser(RegisteredUser recipeUser) {
//		this.recipeUser = recipeUser;
//	}

	public List<String> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<String> cuisines) {
		this.cuisines = cuisines;
	}
	
	
	
	public String getSrcUrl() {
		return srcUrl;
	}


	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}


	public String getSrcTitle() {
		return srcTitle;
	}


	public void setSrcTitle(String srcTitle) {
		this.srcTitle = srcTitle;
	}


	public String getPrepTime() {
		return prepTime;
	}


	public void setPrepTime(String prepTime) {
		this.prepTime = prepTime;
	}


	public String getServings() {
		return servings;
	}


	public void setServings(String servings) {
		this.servings = servings;
	}


	public List<String> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}


	public void set(Recipe recipe) {
		this.setContent(recipe.getContent() != null ? recipe.getContent() : this.getContent());
		this.setTitle(recipe.getTitle() != null ? recipe.getTitle() : this.getTitle());
		this.setImageUrl(recipe.getImageUrl() != null ? recipe.getImageUrl() : this.getImageUrl());
		//this.setRecipeUser(recipe.getRecipeUser() != null ? recipe.getRecipeUser() : this.getRecipeUser());
		this.setSrcUrl(recipe.getSrcUrl() != null ? recipe.getSrcUrl() : this.getSrcUrl());
		this.setServings(recipe.getServings() != null ? recipe.getServings() : this.getServings());
		this.setPrepTime(recipe.getPrepTime() != null ? recipe.getPrepTime() : this.getPrepTime());
		this.setSrcTitle(recipe.getSrcTitle() != null ? recipe.getSrcTitle() : this.getSrcTitle());

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
		
		if (recipe.getIngredients()!= null) {
			if (this.getIngredients() == null) {
				this.setIngredients(recipe.getIngredients());
			} else if (!recipe.getIngredients().equals(this.getIngredients())) {
				this.setIngredients(recipe.getIngredients());
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
