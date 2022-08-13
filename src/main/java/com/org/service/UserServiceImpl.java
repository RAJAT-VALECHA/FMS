package com.org.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.UserDao;
import com.org.exceptions.RecordNotFoundException;
import com.org.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userRepo;
	
	@Override
	public User addUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public User updateUser(User user){
		return userRepo.save(user);
	}

	@Override
	public void removeUser(long userid) throws RecordNotFoundException {
	Optional<User> findBookingById = userRepo.getByUserId(userid);
	if (findBookingById.isPresent()) {
		userRepo.deleteById(userid);
	}
	else
	throw new RecordNotFoundException("User with Id: " + userid + "does not exist");
	}
	
	@Override
	public User viewUser(long userid) throws RecordNotFoundException {
		return userRepo.findByUserId(userid);
	}
	
	@Override
	public List<User> viewUser() throws RecordNotFoundException {
		return userRepo.findAll();
	}
	
	@Override
	public User findUserById(long userId) {
		return userRepo.findByUserId(userId);
	}

	@Override
	public Optional<User> getUserById(long empId) {
		return userRepo.findById(empId);
		
	}


}