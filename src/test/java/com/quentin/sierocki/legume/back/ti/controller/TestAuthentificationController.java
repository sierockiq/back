package com.quentin.sierocki.legume.back.ti.controller;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import com.quentin.sierocki.legume.back.controller.AuthenticationController;
import com.quentin.sierocki.legume.back.controller.ControllerException;
import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.service.ServiceException;
import com.quentin.sierocki.legume.back.ti.SpringBootRestApplicationTest;
import com.quentin.sierocki.legume.back.ti.util.Builder;

public class TestAuthentificationController extends SpringBootRestApplicationTest {
	@Autowired
	AuthenticationController authController;

	// save in error
	@Test(expected = ValidationException.class)
	public void saveUser_null() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		authController.saveUser(null);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_username_empty() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setUsername("");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_bad_pĥone() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setPhone("58");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	@Order(2)
	public void saveUser_bad_password() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setPassword("");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	@Order(2)
	public void saveUser_bad_lat_lng() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setLat(0.0f);
		userDTO.setLng(0.0f);
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_bad_adress() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setAdress("");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_bad_city() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setCity("");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_empty_email() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setEmail("");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test(expected = ValidationException.class)
	public void saveUser_ad_format_email() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		userDTO.setEmail("coxzekoaek");
		authController.saveUser(userDTO);
		Assert.fail();
	}

	@Test
	public void saveUser_ok() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		authController.saveUser(userDTO);
		UserDAO user = userRepository.findByUsername(userDTO.getUsername());
		Assert.assertNotNull(user);
		Assert.assertEquals(userDTO.getUsername(), user.getUsername());
		
	}

	@Test(expected = ControllerException.class)
	public void saveUser_2timeSameUserName() throws ServiceException, ValidationException, ConvertionException, ControllerException {
		UserDTO userDTO = Builder.createUser();
		authController.saveUser(userDTO);
		authController.saveUser(userDTO);
		Assert.fail();
	}
	
	@Test(expected = Exception.class)
	public void loginKo() throws Exception {
		authController.login(Builder.createUser());
		Assert.fail();
	}
	
	@Test
	@Transactional
	public void loginOk() throws Exception {
		UserDTO userDTO = Builder.createUser();
		authController.saveUser(userDTO);
		ResponseEntity<?> response = authController.login(Builder.createUser());
		UserDTO userResult = (UserDTO) response.getBody();
		Assert.assertEquals(userResult.getUsername(),"quentin");
		Assert.assertNotNull(userResult.getToken());
	}
	
	/*
	@Test(expected = Exception.class)
	public void isTokenValid_malformed_token() throws Exception {
		ResponseEntity<?> response = authController.isTokenValid("caca");
		String responseStr = response.getBody().toString();
		Assert.fail();
	}
	
	@Test(expected = Exception.class)
	public void isTokenValid_bad_token() throws Exception {
		ResponseEntity<?> response = authController.isTokenValid("ayJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4OCIsInVpZCI6Ijg4IiwiZXhwIjoxNTg3MjMwMDE4LCJpYXQiOjE1ODcyMjY0MTh9.1OCUbxtDh4KnliEJjHzHmR_k1c-ZowTlhjkqBseSe81adrUSO4Wxde-RnymCX7o_FuCD-a7kWbx4mxPEQ4uQaQ");
		String responseStr = response.getBody().toString();
		Assert.fail();
	}
	@Test
	public void isValidToken() throws Exception {
		UserDTO userDTO = Builder.createUser();
		authController.saveUser(userDTO);
		ResponseEntity<?> response = authController.login(Builder.createJWTRequest());
		Hashtable table = (Hashtable)response.getBody();
		String token = (String) table.get("jwtToken");
		System.out.println(token);
		ResponseEntity<?> response2 = authController.isTokenValid(token);
		String responseStr = response2.getBody().toString();
		System.out.println(response2);
		Assert.assertTrue(Boolean.valueOf(responseStr));
	}*/
	

}
