package edu.northeastern.cs5610.models;

import java.util.Date;

import javax.persistence.Entity;

/**
 * Represents the Moderator Class. 
 *
 */
@Entity
public class Moderator extends User {

	// instance variables
	private Date startDate;


	/**
	 * Default Constructor
	 */
	public Moderator() {
		super();
	}

	// Getters and Setters
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Updates current moderator
	 * 
	 * @param admin
	 */
	public void set(Moderator admin) {

		super.set(admin);
		this.setStartDate(admin.getStartDate() != null ? admin.getStartDate() : this.getStartDate());

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
