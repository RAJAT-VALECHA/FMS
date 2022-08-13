package com.org.service;

import java.util.List;
import java.util.Optional;


import com.org.exceptions.RecordNotFoundException;
import com.org.model.User;

public interface UserService {

	public User addUser(User user);
	public User viewUser(long userid) throws RecordNotFoundException;
	public List<User> viewUser() throws RecordNotFoundException;
	public User updateUser(User user) throws RecordNotFoundException;
	public void removeUser(long userid) throws RecordNotFoundException;
	public User findUserById(long userId);
	public Optional<User> getUserById(long empId);
}