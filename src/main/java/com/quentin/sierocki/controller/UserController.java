package com.quentin.sierocki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.UserService;
import com.quentin.sierocki.service.converter.ConvertionException;

@RestController
//@CrossOrigin(origins = "http://localhost:8082")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/user/{idUser}")
	public UserDTO getUser(@PathVariable int idUser) throws ConvertionException,  FunctionnalException {
		return userService.findUserById(idUser);
	}

	@GetMapping("/users")
	public List<UserDTO> getUsers() throws ConvertionException, FunctionnalException {
		return userService.findAllUsers();
	}
}
