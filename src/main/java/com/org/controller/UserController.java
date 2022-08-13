package com.org.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.org.dao.UserDao;
import com.org.exceptions.RecordNotFoundException;
import com.org.exceptions.UserNotFoundException;
import com.org.model.Booking;
import com.org.model.User;
import com.org.model.UserLogin;
import com.org.service.BookingService;
import com.org.service.UserService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userRepository;
	@Autowired(required= true)
	BookingService bookingService;
	@PostMapping("/register")
	@ApiOperation(value="User Signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmailId().equals(newUser.getEmailId())) {
            	return new ResponseEntity<String>("Email Already Taken", HttpStatus.BAD_REQUEST);
            }
        }
        userRepository.save(newUser);
        return new ResponseEntity<String>("Registration Successful", HttpStatus.OK);   
    }
    
    @PostMapping("/login")
    @ApiOperation(value="User Login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLogin user) throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getEmailId().equals(user.getEmailId()) && other.getUserPassword().equals(user.getUserPassword())) {
            	User us =  userRepository.getUserByEmailId(user.getEmailId()).orElseThrow(()->new UserNotFoundException("No User Found with this Username: "+user.getEmailId()));            
            	if(us.isLoggedIn())
            		return new ResponseEntity<String>("Already Logged in", HttpStatus.BAD_REQUEST);
            	us.setLoggedIn(true);
            	userService.updateUser(us);
            	return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Invalid Login", HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/logout")
    @ApiOperation(value="User Logout")
    public ResponseEntity<String> logUserOut(@Valid @RequestBody UserLogin user) throws UserNotFoundException {
    	List<User> users = userRepository.findAll();
        for (User other : users) {
        	if (other.getEmailId().equals(user.getEmailId()) && other.getUserPassword().equals(user.getUserPassword())) {            	
        		User us =  userRepository.getUserByEmailId(user.getEmailId()).orElseThrow(()->new UserNotFoundException("No User Found with this Username: "+user.getEmailId()));
            	if(!us.isLoggedIn())
            		return new ResponseEntity<String>("Already Logged out", HttpStatus.BAD_REQUEST);
            	us.setLoggedIn(false);
            	userService.updateUser(us);
            	return new ResponseEntity<String>("Logout Successful", HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
    }
	
	@GetMapping("/read/all")
	public List<User> readAllUsers() throws RecordNotFoundException {
			return userService.viewUser();
	}
	
	@GetMapping("/search/byid/{id}")
	public ResponseEntity<User> searchUserByID(@PathVariable("id") long userId) throws RecordNotFoundException {
		User user = userService.getUserById(userId).orElse(null);
		return ResponseEntity.ok().body(user);
	}


	@DeleteMapping("/delete/{id}")
	public String removeUser(@PathVariable("id") long userId) throws RecordNotFoundException {
		userService.removeUser(userId);
		return "deleted";
	}
	
	
	
	@GetMapping("/byid/{id}")
	@ApiOperation(value="Get user by id")
	public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId)
	{
		User user = userService.getUserById(userId).orElse(null);
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/update/byid/{id}")
	@ApiOperation(value="Update existing user")
	public ResponseEntity<User> updateUserById(@PathVariable(value="id") Long userId,@Valid @RequestBody User userinfo) throws UserNotFoundException
	{
		User user = userService.getUserById(userId).orElseThrow(()-> new UserNotFoundException("User not Found"));
		user.setEmailId(userinfo.getEmailId());
		user.setPhoneNumber(userinfo.getPhoneNumber());
		user.setUserName(userinfo.getUserName());
		user.setUserPassword(userinfo.getUserPassword());
		User updatedUser = userService.updateUser(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	
	
	@PostMapping("/booking/create/{uId}")
	public ResponseEntity<?> addBooking(@PathVariable("uId") long userId,@RequestBody Booking newBooking) throws UserNotFoundException {
		User user = userService.getUserById(userId).orElseThrow(()-> new UserNotFoundException("User not Found"));
        if(user.isLoggedIn()) {
        	return bookingService.createBooking(newBooking,userId);
        }
        else 
        	return new ResponseEntity<String>("Login before booking",HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/booking/read/all")
	public Iterable<Booking> readAllBookings() {

		return bookingService.displayAllBooking();
	}

//	@PutMapping("/updateBooking")
//	public void modifyBooking(@RequestBody Booking updateBooking) {
//
//		bookingService.updateBooking(updateBooking);
//	}

	@GetMapping("/searchBooking/byid/{id}")
	public ResponseEntity<?> searchBookingByID(@PathVariable("id") long bookingId) {
		
		return bookingService.findBookingById(bookingId);
	}

	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}
}