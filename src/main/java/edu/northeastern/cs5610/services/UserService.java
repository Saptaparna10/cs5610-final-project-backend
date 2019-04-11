package edu.northeastern.cs5610.services;

import org.springframework.beans.factory.annotation.Autowired;

import edu.northeastern.cs5610.repositories.*;

public class UserService {

	@Autowired
	UserRepository repository;
}
