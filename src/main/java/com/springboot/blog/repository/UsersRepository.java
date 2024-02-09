package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.blog.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	
	//Users findByEmail(String email);
	//Users findByUsername(String username);
	//Users findByUsernameOrEmail(String username, String email);

	Optional<Users> findByEmail(String email);
	Optional<Users> findByUsername(String username);
	Optional<Users> findByUsernameOrEmail(String username, String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	Users findByEmailAndPassword(String email, String password);
	
	@Query("from Users As u where u.email=:email")
	Users findByEmailAddress(String email);
	
	@Query("from Users As u where u.password=:password")
	Users findByPassword(String password);
	

}
