package edu.northeastern.cs5610.models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Moderator Class. 
 *
 */
@Entity
public class Moderator extends User {

	// instance variables
	private Date startDate;
	
	@OneToMany(mappedBy = "moderator",cascade=CascadeType.ALL,orphanRemoval=true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<RecipeList> recipeLists;
	
	@ManyToMany(mappedBy = "following",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<RegisteredUser> followers;


	/**
	 * Default Constructor
	 */
	public Moderator() {
		super();
		followers= new ArrayList<>();
		recipeLists= new ArrayList<>();
	}

	public List<RecipeList> getRecipeLists() {
		return recipeLists;
	}

	public void setRecipeLists(List<RecipeList> recipeLists) {
		this.recipeLists = recipeLists;
	}

	// Getters and Setters
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<RegisteredUser> getFollowers() {
		return followers;
	}

	public void setFollowers(List<RegisteredUser> followers) {
		this.followers = followers;
	}
	

	/**
	 * Updates current moderator
	 * 
	 * @param admin
	 */
	public void set(Moderator user) {

		super.set(user);
		this.setStartDate(user.getStartDate() != null ? user.getStartDate() : this.getStartDate());
		
		if (user.getFollowers() != null) {
			if (this.getFollowers() == null) {
				this.setFollowers(user.getFollowers());
			} else if (!this.getFollowers().equals(user.getFollowers())) {
				this.setFollowers(user.getFollowers());
			}
		}
		if (user.getRecipeLists() != null) {
			if (this.getRecipeLists() == null) {
				this.setRecipeLists(user.getRecipeLists());
			} else if (!this.getRecipeLists().equals(user.getRecipeLists())) {
				this.setRecipeLists(user.getRecipeLists());
			}
		}
	}


	/**
	 * Overridden version of the equals method Two moderator are considered equal only
	 * if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Moderator) {
			Moderator admin = (Moderator) obj;
			if (this.getId() == admin.getId()) {
				return true;
			}
		}
		return false;
	}

}
