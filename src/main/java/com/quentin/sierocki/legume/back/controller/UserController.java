package com.quentin.sierocki.legume.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.converter.DAOToDTOConverter;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.service.ServiceException;
import com.quentin.sierocki.legume.back.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/user")
	public UserDTO getUser(@RequestHeader long idUser) throws ControllerException {
		try {
			return DAOToDTOConverter.userDAOToUserDTO(userService.findUserById(idUser));
		} catch (ServiceException | ConvertionException e) {
			throw new ControllerException(e.getMessageRetour(),"UserController->getUser" + e.getPathMethod(), e.getMessage(), e);
		}

	}

	@GetMapping("/users")
	public List<UserDTO> getUsers(@RequestHeader long idUser) throws ControllerException {
		try {
			return userService.findAllUsers();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessageRetour(),"UserController->getUsers" + e.getPathMethod(), e.getMessage(), e);
		}

	}
}
