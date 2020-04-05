
package com.quentin.sierocki.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.domain.repository.UserRepository;
import com.quentin.sierocki.service.converter.DAOToDTOConverter;
import com.quentin.sierocki.service.converter.DTOToDAOConverter;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public UserDTO findUserById(int idUser) {
		UserDAO user = userRepository.findById(idUser).orElse(null);
		return DAOToDTOConverter.userDAOToUserDTO(user);
	}

	public List<UserDTO> findAllUsers() {
		List<UserDAO> users = userRepository.findAll();
		return users.stream().map(user -> DAOToDTOConverter.userDAOToUserDTOWithoutCommands(user))
				.collect(Collectors.toList());
	}

	public UserDTO findUserByUserName(String userName) {
		return DAOToDTOConverter.userDAOToUserDTO(userRepository.findByUsername(userName));
	}

	public UserDTO save(UserDTO user) {
		UserDAO userExist = userRepository.findByUsername(user.getUsername());
		if (userExist == null) {
			UserDAO newUser = DTOToDAOConverter.userDTOToUserDAO(user);
			newUser.setUsername(user.getUsername());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			return DAOToDTOConverter.userDAOToUserDTO(userRepository.save(newUser));
		} else {
			return DAOToDTOConverter.userDAOToUserDTO(userExist);
		}
	}
}
