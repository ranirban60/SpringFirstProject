package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.LogoutDto;
import com.bridgelabz.dto.RegisterDto;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.EmailDetails;
import com.bridgelabz.model.UserModel;

public interface IUserService {

	ResponseEntity add(UserModel user);

	List<UserModel> findAll();
	
	List<UserDto> getAllUser(String role);

	UserDto findById(int id);

	void deleteById(int id);

	UserModel updateOneUser(UserModel user, int id);

	UserDto getUserByName(String name);

	RegisterDto register(RegisterDto user);

	LoginDto getUserByLogin(String email, String password);

	ResponseEntity getUserByLogin(LoginDto loginDto);

	public UserDto getByUserLogin(LoginDto loginDto);

	public String getToken(LoginDto loginDto);

	UserDto getUserByLogin(String token);

	UserDto updateUserByToken(UserDto user, String token);

	LogoutDto logoutByToken(String token);

//	String sendSimpleMail(EmailDetails details);






}
