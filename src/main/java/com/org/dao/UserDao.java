package com.org.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.model.User;

public interface UserDao extends JpaRepository<User, Long>{
	User findByUserId(long userid);
	Optional<User> getByUserId(long userid);
	User findByEmailId(String emailId);
	Optional<User> getUserByEmailId(String emailId);
}