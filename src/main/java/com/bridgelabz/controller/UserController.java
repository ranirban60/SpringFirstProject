package com.bridgelabz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.LogoutDto;
import com.bridgelabz.dto.RegisterDto;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.EmailDetails;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.service.IUserService;
import com.bridgelabz.utility.JavaEmailService;

@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@Autowired
	private JavaEmailService emailService;

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/hello")
	public String User() {
		
		logger.info("Inside info");
		logger.error("Inside error");
		logger.warn("Warning");
		logger.debug("Inside debugging");
		logger.trace("For tracing");
		return "Hello";
	}

	@PostMapping("/addUser")
	public ResponseEntity AddUser(@RequestBody UserModel user) {
		ResponseEntity userModel = userService.add(user);
		System.out.println("User is added");
		return new ResponseEntity(userModel, "User added succesfully");
	}

	@GetMapping("/listAll")
	public ResponseEntity listAll() {
		List<UserModel> users = userService.findAll();
		System.out.println("Getting the list of all user");
		return new ResponseEntity(users, "Get the users succesfully");
	}

	@GetMapping("/getAllUser")
	public List<UserDto> getAllUser(@RequestParam String role){
		return this.userService.getAllUser(role);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity getUser(@PathVariable int id) {
		UserDto user = userService.findById(id);
		System.out.println("Getting the user by id where Id is " + id);
		return new ResponseEntity(user, "Get the user succesfully");
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity updateUser(@RequestBody UserModel user, @PathVariable int id) {
		UserModel userModel = userService.updateOneUser(user, id);
		System.out.println("Update the user succesfully");
		return new ResponseEntity(userModel, "Update the user succesfully");
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity deleteOneUser(@RequestParam int id) {
		userService.deleteById(id);
		System.out.println("Delete one user");
		return new ResponseEntity(userService, "Delete the user succesfully");
	}

	@GetMapping("/searchUser")
	public ResponseEntity getUserByName(@RequestParam String name) {
		UserDto user = userService.getUserByName(name);
		System.out.println("User fetched successfully");
		return new ResponseEntity(user, "User fetched successfully");
	}

	@PostMapping("/registerUser")
	public ResponseEntity registerUser(@RequestBody RegisterDto user) {
		RegisterDto registerUser = userService.register(user);
		System.out.println("Registration is done");
		return new ResponseEntity(registerUser, "User registered succesfully");
	}

	@GetMapping("/userLogin")
	public ResponseEntity loginUser(@RequestParam String email, String password) {
		LoginDto loginUser = userService.getUserByLogin(email, password);
		System.out.println("Login is done");
		return new ResponseEntity(loginUser, "Login successfully");
	}

	//@GetMapping("/login")
	public ResponseEntity getUserByLoginDto(@RequestBody LoginDto loginDto) {
		return userService.getUserByLogin(loginDto);

	}

	@GetMapping("/loginUser")
	public ResponseEntity loginUser(@RequestBody LoginDto loginDto) {
		UserDto login = userService.getByUserLogin(loginDto);
		return new ResponseEntity(login, "Login successfully");

	}

	@GetMapping("/login")
	public ResponseEntity userToken(@RequestBody LoginDto loginDto) {
		String token = userService.getToken(loginDto);
		return new ResponseEntity(token,"Login Successfully");

	}

	@GetMapping("/fetchingUserAfterLogin")
	public ResponseEntity getUserByLogin(@RequestHeader String token) {
		UserDto userDto = userService.getUserByLogin(token);
		return new ResponseEntity(userDto, "Fetched user details");
	}

	@PutMapping("/updateUserByToken")
	public ResponseEntity updateByToken(@RequestBody UserDto user, @RequestHeader String token) {
		UserDto userDto = userService.updateUserByToken(user, token);
		System.out.println("Update the user succesfully");
		return new ResponseEntity(userDto, "Update the user succesfully");
	}

	@GetMapping("/logoutUserByToken")
	public ResponseEntity logoutUserbyToken(@RequestHeader String token) {
		LogoutDto logOut = userService.logoutByToken(token);
		return new ResponseEntity(logOut, "User logged out successfully");
	}

//	@PostMapping("/sendMail")
//	public String sendMail(@RequestBody EmailDetails details) {
//		String status = emailService.sendSimpleMail(details);
//		return status;
//	}
	
	@GetMapping("/token")
	public String UserToken(@RequestParam String token) {
		return "Registered successfully";
	}
}
