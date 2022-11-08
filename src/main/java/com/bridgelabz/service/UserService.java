package com.bridgelabz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.LogoutDto;
import com.bridgelabz.dto.RegisterDto;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.IUserRepository;
import com.bridgelabz.utility.JavaEmailService;
import com.bridgelabz.utility.JwtTokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	JavaEmailService javaEmailService;

//	@Autowired
//	RestTemplate restTemplate;

	@Override
	public ResponseEntity add(UserModel user) {
		String userName = user.getFirstName();
		if (userRepo.findByFirstName(userName).isPresent()) {
			throw new UserException("User already exist", "Try to add different user name");
		} else {
			String token = jwtTokenUtil.generateToken(user.getEmail(), user.getPassword());
			javaEmailService.sendSimpleMail(user.getEmail(), token, "Verification");
			// try {
			// Thread.sleep(120000);
			// restTemplate.getForObject("http://localhost:8080/token", String.class);
			user.setIsVerified(true);
			UserModel userModel = userRepo.save(user);
			UserDto addUser = modelMapper.map(userModel, UserDto.class);
			return new ResponseEntity(addUser, "One user added");
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//			return null;
		}
	}

	@Override
	public List<UserModel> findAll() {
		List<UserModel> users = userRepo.findAll();
		return users;
	}
	
	@Override
	public List<UserDto> getAllUser(String role){
		if(role.equals("Admin")) {
			return userRepo.findAll().stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
		}
		else {
			throw new UserException("Not admin", "Check your role");
		}
	}

	@Override
	public UserDto findById(int id) {
		Optional<UserModel> findById = userRepo.findById(id);
		// System.out.println("Find by id"+findById.get());
		if (findById.isEmpty()) {
			throw new UserException(" User does not exist", "Enter valid user id");
		}
		UserDto userDto = modelMapper.map(findById.get(), UserDto.class);
		return userDto;
	}

	@Override
	public void deleteById(int id) {
		if (userRepo.findById(id).isPresent()) {
			userRepo.deleteById(id);
		} else {
			throw new UserException("User is not deleted", "Enter valid Id");
		}
	}

	@Override
	public UserModel updateOneUser(UserModel user, int id) {
		if (userRepo.existsById(id)) {
			user.setId(id);
			userRepo.save(user);
		}
		return user;
	}

	@Override
	public UserDto getUserByName(String name) {
		Optional<UserModel> findByName = userRepo.findByFirstName(name);
		if (findByName.isEmpty()) {
			throw new UserException("User does not exist", "Enter valid user name");
		}
		UserDto userDto = modelMapper.map(findByName.get(), UserDto.class);
		return userDto;

	}

	@Override
	public RegisterDto register(RegisterDto user) {
		Optional<UserModel> userModel = userRepo.findByEmail(user.getEmail());
		if (userModel.isPresent()) {
			throw new UserException(" User is exist", "Enter new email");
		}
		UserModel registeredUser = modelMapper.map(user, UserModel.class);
		userRepo.save(registeredUser);
		System.out.println("Successfully registered");
		return user;
	}

	@Override
	public LoginDto getUserByLogin(String email, String password) {
		Optional<UserModel> loginUser = userRepo.findByEmail(email);
		if (loginUser.isPresent()) {
			LoginDto loginUserDto = modelMapper.map(loginUser.get(), LoginDto.class);
			return loginUserDto;
		} else {
			throw new UserException(" User not found", "Enter valid email id");
		}
	}

	@Override
	public ResponseEntity getUserByLogin(LoginDto loginDto) {
		System.out.println(loginDto.getEmail() + "" + loginDto.getPassword());
		if (userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).isPresent()) {
			UserModel loginUser = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword()).get();

			UserDto loginUserDto = modelMapper.map(loginUser, UserDto.class);
			return new ResponseEntity(loginUserDto, "User login successfully");
		} else {
			throw new UserException(" User not found", "Enter valid email id");
		}
	}

	@Override
	public UserDto getByUserLogin(LoginDto loginDto) {
//		UserDto userDto = new UserDto();
		Optional<UserModel> userModel = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
//		String userEmail = userModel.get().getEmail();
//		String userPassword = userModel.get().getPassword();
//		System.out.println("UserEmail" + userEmail + loginDto.getEmail());
//		System.out.println("User Password" + userPassword + loginDto.getPassword());
//		if (userEmail.equals(loginDto.getEmail())) {
//			if (userPassword.equals(loginDto.getPassword())) {
//				userDto = modelMapper.map(userModel.get(), UserDto.class);
//				return userDto;
//			}
//			throw new UserException("Password is incorrect", "Give correct password");
//		}
//		throw new UserException("Email is incorrect", "Give correct Email");
//	}
		if (userModel.isEmpty()) {
			Optional<UserModel> userEmail = userRepo.findByEmail(loginDto.getEmail());
			Optional<UserModel> userPassword = userRepo.findByPassword(loginDto.getPassword());
			if (userEmail.isEmpty()) {
				throw new UserException("Email is incorrect", "Give correct Email");
			} else if (userPassword.isEmpty()) {
				throw new UserException("Password is incorrect", "Give correct password");
			}
		}
		UserDto userDto = modelMapper.map(userModel.get(), UserDto.class);
		System.out.println("Get the data successfully ");
		return userDto;
	}

	@Override
	public String getToken(LoginDto loginDto) {
		Optional<UserModel> user = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		if (user.isEmpty()) {
			Optional<UserModel> userEmail = userRepo.findByEmail(loginDto.getEmail());
			Optional<UserModel> userPassword = userRepo.findByPassword(loginDto.getPassword());
			if (userEmail.isEmpty()) {
				throw new UserException("Email is incorrect", "Give correct Email");
			} else if (userPassword.isEmpty()) {
				throw new UserException("Password is incorrect", "Give correct password");
			}
		}
		String token = jwtTokenUtil.generateToken(loginDto);
		user.get().setIsLogin(true);
		userRepo.save(user.get());
		System.out.println("Check the user is login or not " + user.get().getIsLogin());

		return token;
	}

	@Override
	public UserDto getUserByLogin(String token) {
		LoginDto loginDto = jwtTokenUtil.decode(token);
		Optional<UserModel> userModel = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		if (userModel.get().getIsLogin().equals(true)) {
			UserDto userDto = modelMapper.map(userModel.get(), UserDto.class);
			System.out.println("Get the data successfully ");
			return userDto;
		} else {
			throw new UserException("Data not found", "Please login");
		}
	}

	@Override
	public UserDto updateUserByToken(UserDto user, String token) {
		LoginDto loginUser = jwtTokenUtil.decode(token);
		UserModel users = modelMapper.map(user, UserModel.class);
		
		if (userRepo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).isPresent()
				&& userRepo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).get().getIsLogin()){
			users.setId(userRepo.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()).get().getId());
			users.setIsVerified(true);
			users.setIsLogin(true);
			userRepo.save(users);
			return user;
		} else {
			throw new UserException("Not logged in", "Please login again");
		}
	}

	@Override
	public LogoutDto logoutByToken(String token) {
		LoginDto loginDetails = jwtTokenUtil.decode(token);
		Optional<UserModel> checkUserDetails = userRepo.findByEmailAndPassword(loginDetails.getEmail(),
				loginDetails.getPassword());
		LogoutDto logout = modelMapper.map(checkUserDetails, LogoutDto.class);
		checkUserDetails.get().setIsLogin(false);
		userRepo.save(checkUserDetails.get());
		return logout;
	}

//	@Override
//	public String sendSimpleMail(EmailDetails  details) {
//		return javaEmailService.sendSimpleMail(details);
//	}
}