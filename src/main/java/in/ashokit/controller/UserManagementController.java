package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.bindings.User;
import in.ashokit.service.UserMgmtService;

@RestController
public class UserManagementController {

	@Autowired
	UserMgmtService service;
	
	@PostMapping("/saveUser")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		boolean savedUser = service.saveUser(user);
		if(savedUser) {
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}else {
			
			return new ResponseEntity<User>(user,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
