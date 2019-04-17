package edu.northeastern.cs5610.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5610.models.User;
import edu.northeastern.cs5610.repositories.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class UserService {

	@Autowired
	UserRepository repository;

	@PostMapping("/api/register")
	public User register(@RequestBody User newUser,
			HttpSession session) {
		
		List<User> users= findAllUsers();
		
		for (User user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return null;
			}
		}
		return repository.save(newUser);
	}

	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");	
		return currentUser;
	}

	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@PostMapping("/api/login")
	public User login(@RequestBody User credentials,
			HttpSession session) {
		
		List<User> users= findAllUsers();
		for (User user : users) {
			if( user.getUsername().equals(credentials.getUsername())
					&& user.getPassword().equals(credentials.getPassword())) {
				session.setAttribute("currentUser", user);
				return user;
			}
		}
		return null;
	}

	@GetMapping("/api/users")
	List<User> findAllUsers() {
		return (List<User>) repository.findAll();
	}

	@GetMapping("/api/users/{id}")
	User findUserById(@PathVariable("id") int id) {
		
		Optional<User> op = repository.findById(id);
		User user = null;
		if (op.isPresent())
			user = op.get();
		return user;
	}
	
	@PutMapping("/api/user/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
		User prevUser = findUserById(id);
		if(prevUser == null)
			return null;
		prevUser.set(user);
		return repository.save(prevUser);
	}

}
