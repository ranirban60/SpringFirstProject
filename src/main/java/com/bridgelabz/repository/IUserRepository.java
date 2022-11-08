package com.bridgelabz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.UserModel;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Integer> {

	Optional<UserModel> findByFirstName(String name);

	Optional<UserModel> findByEmail(String email);

	Optional<UserModel> findByEmailAndPassword(String email, String password);

	Optional<UserModel> findByPassword(String password);

//	Optional<UserModel> findByNameOrEmail(String name, String email);

}
