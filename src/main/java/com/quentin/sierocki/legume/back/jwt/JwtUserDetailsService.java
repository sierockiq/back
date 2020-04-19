package com.quentin.sierocki.legume.back.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.domain.repository.UserRepository;
import com.quentin.sierocki.legume.back.exception.ResourceNotFoundException;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {

		UserDAO user = userRepository.findByUsername(username);

		if (user == null) {
			throw new ResourceNotFoundException("User", "username", username);
		}

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());

	}

}