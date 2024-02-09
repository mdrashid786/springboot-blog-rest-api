package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{
	
	Optional<Roles> findByName(String name);

}
