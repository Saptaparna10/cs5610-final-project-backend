package edu.northeastern.cs5610.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cuisine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
}
