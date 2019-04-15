package edu.northeastern.cs5610.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5610.models.Moderator;
import edu.northeastern.cs5610.models.RegisteredUser;
import edu.northeastern.cs5610.models.User;
import edu.northeastern.cs5610.repositories.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class RegisteredUserService {

	@Autowired
	RegisteredUserRepository repository;
	
	@Autowired
	ModeratorService modService;
	
	@PostMapping("/api/registeredUser/register")
	public User register(@RequestBody RegisteredUser newUser,
			HttpSession session) {
		
		List<RegisteredUser> users= (List<RegisteredUser>) repository.findAll();
		
		for (User user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return null;
			}
		}
		return repository.save(newUser);
	}
	
	@GetMapping("/api/registereduser/{id}")
	public RegisteredUser findRegisteredUserById(@PathVariable("id") int id) {
		Optional<RegisteredUser> registeredUser =  repository.findById(id);
		if(registeredUser != null) {
			return registeredUser.get();
		}
		else {
			return null;
		}
	}
	

	@PutMapping("/api/registereduser/{id}")
	public RegisteredUser updateRegisteredUser(@PathVariable("id") int id, @RequestBody RegisteredUser user) {
		RegisteredUser prevUser = findRegisteredUserById(id);
		prevUser.set(user);
		return repository.save(prevUser);
	}
	
	@PutMapping("/api/registereduser/follow/{userid}/{moderatorId}")
	public RegisteredUser followModerator(@PathVariable("userid") int userid, @PathVariable("moderatorId") int moderatorId) {
		RegisteredUser user = findRegisteredUserById(userid);
		Moderator mod = modService.findModeratorById(moderatorId);
		
		user.addModeratorToFollowing(mod);
		modService.updateModerator(moderatorId, mod);
		return updateRegisteredUser(userid, user);
		
	}
	
	@DeleteMapping("/api/registereduser/follow/{userid}/{moderatorId}")
	public RegisteredUser unfollowModerator(@PathVariable("userid") int userid, @PathVariable("moderatorId") int moderatorId) {
		RegisteredUser user = findRegisteredUserById(userid);
		Moderator mod = modService.findModeratorById(moderatorId);
		
		user.removeModeratorFromFollowing(mod);
		modService.updateModerator(moderatorId, mod);
		return updateRegisteredUser(userid, user);
	}
	
	@GetMapping("/api/registereduser/{id}/following")
	public List<Moderator> getModeratorsFollowing(@PathVariable ("id") int id){
		RegisteredUser user = findRegisteredUserById(id);
		if(user != null) {
			return user.getFollowing();
		}
		return null;
	}
}
