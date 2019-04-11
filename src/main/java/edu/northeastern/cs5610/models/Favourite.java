package edu.northeastern.cs5610.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Favourite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	private RegisteredUser user;
	
	@ManyToOne
	private Recipe favrecipe;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public Recipe getFavrecipe() {
		return favrecipe;
	}

	public void setFavrecipe(Recipe favrecipe) {
		this.favrecipe = favrecipe;
	}
	
}
