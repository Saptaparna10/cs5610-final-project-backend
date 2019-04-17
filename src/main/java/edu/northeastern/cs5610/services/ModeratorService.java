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

import edu.northeastern.cs5610.models.Moderator;
import edu.northeastern.cs5610.models.RecipeList;
import edu.northeastern.cs5610.models.RegisteredUser;
import edu.northeastern.cs5610.models.User;
import edu.northeastern.cs5610.repositories.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class ModeratorService {
	
	@Autowired
	ModeratorRepository repository;
	
	@PostMapping("/api/moderator/register")
	public User register(@RequestBody Moderator newUser,
			HttpSession session) {
		
		List<Moderator> users= (List<Moderator>) repository.findAll();
		
		for (User user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return null;
			}
		}
		return repository.save(newUser);
	}

	@GetMapping("/api/moderator/{id}")
	public Moderator findModeratorById(@PathVariable("id") int id) {
		Optional<Moderator> moderator =  repository.findById(id);
		if(moderator.isPresent()) {
			return moderator.get();
		}
		else {
			return null;
		}
	}
	
	@PutMapping("/api/moderator/{id}")
	public Moderator updateModerator(@PathVariable("id") int id, @RequestBody Moderator user) {
		Moderator prevUser = findModeratorById(id);
		
		if(prevUser == null)
			return null;
		
		prevUser.set(user);
		return repository.save(prevUser);
	}
	
	@GetMapping("/api/moderator/{id}/recipelists")
	public List<RecipeList> getAllRecipelists(@PathVariable ("id") int id) {
		Moderator mod = findModeratorById(id);
		if(mod != null) {
			return mod.getRecipeLists();
		}
		return null;
	}
	
	@GetMapping("/api/moderator/registereduser/follower/{id}")
	public List<RegisteredUser> findAllFollowerRegUsers(@PathVariable("id") int id){
		Moderator mod =  findModeratorById(id);
		if(mod == null)
			return null;
		return mod.getFollowers();
	}
}
