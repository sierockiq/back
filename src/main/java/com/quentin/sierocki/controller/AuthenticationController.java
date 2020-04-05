package com.quentin.sierocki.controller;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.jwt.JwtRequest;
import com.quentin.sierocki.jwt.JwtTokenUtil;
import com.quentin.sierocki.jwt.JwtUserDetailsService;
import com.quentin.sierocki.service.AuthenticationService;
import com.quentin.sierocki.service.UserService;
import com.quentin.sierocki.service.converter.DAOToDTOConverter;
import com.quentin.sierocki.websecurityconfig.UserDetails;

@RestController
//@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticationService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDTO user = userService.findUserByUserName(authenticationRequest.getUsername());
		final UserDetails userDetails = jwtUserDetailsService.loadUser(user);
		final String jwtToken = jwtTokenUtil.generateToken(userDetails);

		Hashtable loggedInUserInfo = new Hashtable();
		loggedInUserInfo.put("user", user);
		loggedInUserInfo.put("jwtToken", jwtToken);

		return ResponseEntity.ok(loggedInUserInfo);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userService.save(user));
	}
	
	@RequestMapping(value = "/isTokenValid", method = RequestMethod.GET)
	public ResponseEntity<?> isTokenValid(@RequestHeader String token) throws Exception {
		return ResponseEntity.ok(!jwtTokenUtil.isTokenExpired(token));
	}

}
