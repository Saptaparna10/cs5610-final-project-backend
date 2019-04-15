package edu.northeastern.cs5610.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Represents the User class
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	// instance variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String imgurl;
	private String type;
	
	/**
	 * Represents the default constructor
	 */
	public User() {
		super();
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	/**
	 * Updates person
	 * @param user
	 */
	public void set(User person) {
		this.setEmail(person.getEmail() != null ? person.getEmail() : this.getEmail());
		this.setFirstName(person.getFirstName() != null ? person.getFirstName() : this.getFirstName());
		this.setLastName(person.getLastName() != null ? person.getLastName() : this.getLastName());
		this.setPassword(person.getPassword() != null ? person.getPassword() : this.getPassword());
		this.setPhoneNumber(person.getPhoneNumber() != null ? person.getPhoneNumber() : this.getPhoneNumber());
		this.setUsername(person.getUsername() != null ? person.getUsername() : this.getUsername());
		this.setType(person.getType() != null ? person.getType() : this.getType());
		this.setImgurl(person.getImgurl() != null ? person.getImgurl() : this.getImgurl());
	}
	
	/**
	 * Overridden version of the equals method. Two users are considered
	 * equal only if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User temp = (User) obj;
			return this.id == temp.id;
		}
		return false;
	}
	
	
}
