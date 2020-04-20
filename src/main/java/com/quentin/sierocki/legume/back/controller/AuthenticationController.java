package com.quentin.sierocki.legume.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.converter.DAOToDTOConverter;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.service.AuthenticationService;
import com.quentin.sierocki.legume.back.service.ServiceException;
import com.quentin.sierocki.legume.back.service.UserService;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userLogin) throws Exception {
		UserDTO user = DAOToDTOConverter.userDAOToUserDTO(userService.findUserByUserName(userLogin.getUsername()));
		String token = authenticationService.login(String.valueOf(user.getId()), user.getUsername(),
				userLogin.getPassword());
		user.setToken(token);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws ValidationException, ControllerException {
		try {
			if (user == null)
				throw new ValidationException("Votre utilisateur n'existe pas. Veuillez le créer.", "user est null");

			user.validate();
			return ResponseEntity.ok(DAOToDTOConverter.userDAOToUserDTO(userService.save(user)));
		} catch (ServiceException | ConvertionException e) {
			throw new ControllerException(e.getMessageRetour(), "ProductController->addNewProduct" + e.getPathMethod(),
					e.getMessage(), e);
		}

	}

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		return "coucou";
	}

}
