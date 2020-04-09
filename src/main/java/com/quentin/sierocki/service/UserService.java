
package com.quentin.sierocki.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.domain.repository.UserRepository;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.converter.ConvertionException;
import com.quentin.sierocki.service.converter.DAOToDTOConverter;
import com.quentin.sierocki.service.converter.DTOToDAOConverter;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public UserDTO findUserById(int idUser) throws ConvertionException, FunctionnalException {
		UserDAO user = userRepository.findById(idUser).orElse(null);

		if (user == null)
			throw new FunctionnalException("user not exist : id = " + idUser);

		return DAOToDTOConverter.userDAOToUserDTO(user);
	}

	public List<UserDTO> findAllUsers() throws ConvertionException, FunctionnalException {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();

		List<UserDAO> usersDAO = userRepository.findAll();
		if (usersDAO == null || usersDAO.isEmpty())
			throw new FunctionnalException("liste utilisateurs vides");

		for (UserDAO userDAO : usersDAO) {
			usersDTO.add(DAOToDTOConverter.userDAOToUserDTOWithoutCommands(userDAO));
		}

		return usersDTO;
	}

	public UserDTO findUserByUserName(String userName) throws ConvertionException, FunctionnalException {
		UserDAO userDAO = userRepository.findByUsername(userName);
		if (userDAO == null)
			throw new FunctionnalException("User did not exist | username=" + userName);
		return DAOToDTOConverter.userDAOToUserDTO(userDAO);
	}

	public UserDTO save(UserDTO user) throws ConvertionException {
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
