//package edu.northeastern.cs5610.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;@Entity
//public class Cuisine {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	private String name;
//
//	@ManyToMany
//	@JoinTable(name = "recipeToCuisine")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@JsonIgnore
//	private List<Recipe> recipes;
//	
//	public Cuisine() {
//		recipes= new ArrayList<>();
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public List<Recipe> getRecipes() {
//		return recipes;
//	}
//
//	public void setRecipes(List<Recipe> recipes) {
//		this.recipes = recipes;
//	}
//	
//	public void set(Cuisine cuisine) {
//		this.setName(cuisine.getName() != null ? cuisine.getName() : this.getName());
//		
//		if (cuisine.getRecipes() != null) {
//			if (this.getRecipes() == null) {
//				this.setRecipes(cuisine.getRecipes());
//			} else if (!cuisine.getRecipes().equals(this.getRecipes())) {
//				this.setRecipes(cuisine.getRecipes());
//			}
//		}
//	}
//}
