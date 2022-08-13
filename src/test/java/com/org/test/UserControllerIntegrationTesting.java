package com.org.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.org.FmsSpringBootBackendApplication;
import com.org.model.User;



@SpringBootTest(classes = FmsSpringBootBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTesting {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String getRootUrl() 
	{
		return "http://localhost:"+port;
	}
	@Test
	public void testAddUser()
	{
		User user = new User();
		long id=5;
		user.setUserId(id);
		user.setUserName("David");
		user.setEmailId("david1@gmail.com");
		user.setUserPassword("david123");
		user.setPhoneNumber(903218649);
		
		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/addUser", user, User.class);
		assertNotNull(postResponse.getBody());
	}
	
	@Test
	public void testReadAllUsers()
	{
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null,headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl()+"/api/readAllUsers",HttpMethod.GET,entity,String.class);
		assertNotNull(response.getBody());
	}
	@Test
	public void testSearchUserById()
	{
		BigInteger id=BigInteger.valueOf(3);
		User user = restTemplate.getForObject(getRootUrl()+"/api/readUser/"+id, User.class);
		assertNotNull(user);
	}
	@Test
	public void testUpdateUser()
	{
		long id=4;
		//Load Object
		User user = restTemplate.getForObject(getRootUrl()+"/api/readUser/"+id, User.class);
		user.setUserId(id);
		user.setUserName("David");
		user.setEmailId("david1@gmail.com");
		user.setUserPassword("david123");
		user.setPhoneNumber(903218649);
		
		restTemplate.put(getRootUrl()+"/user/updateUser/"+id, user);
		
		User updatedUser = restTemplate.getForObject(getRootUrl()+"/api/updateUser/"+id, User.class);
		
		assertNotNull(updatedUser);
		assertEquals(user.getUserId(),updatedUser.getUserId());
	}
	@Test
	public void testDeleteUserById()
	{
		BigInteger id=BigInteger.valueOf(4);
		User user = restTemplate.getForObject(getRootUrl()+"/user/readUser/"+id, User.class);
		assertEquals(id, user.getUserId());
		
		restTemplate.delete(getRootUrl()+"/user/deleteUser/"+id,user);
		
		User deletedUser = restTemplate.getForObject(getRootUrl()+"/user/readUser/"+id, User.class);
		assertEquals(id, deletedUser.getUserId());
		
	}
	

}








