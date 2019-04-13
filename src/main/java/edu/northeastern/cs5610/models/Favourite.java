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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((favrecipe == null) ? 0 : favrecipe.hashCode());
		result = prime * result + id;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Favourite) {
			Favourite fav = (Favourite) obj;
			if (this.getId() == fav.getId()) {
				return true;
			}
		}
		return false;
	}
	
}
