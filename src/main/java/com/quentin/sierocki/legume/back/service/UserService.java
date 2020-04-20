
package com.quentin.sierocki.legume.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.legume.back.controller.converter.DAOToDTOConverter;
import com.quentin.sierocki.legume.back.controller.converter.DTOToDAOConverter;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.domain.repository.UserRepository;
import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.legume.back.globals.Constants;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * @param idUser
	 * @return
	 * @throws ServiceException
	 */
	/**
	 * @param idUser
	 * @return
	 * @throws ServiceException
	 */
	public UserDAO findUserById(long idUser) throws ServiceException {
		try {
			UserDAO userDAO = userRepository.findById(idUser).orElse(null);
			if (userDAO == null)
				throw new ServiceException("Le user n'existe pas avec l'id " + idUser);

			return userDAO;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "UserService->findUserById - ", e.getMessage(), e);
		}
	}

	/**
	 * @return
	 * @throws ServiceException
	 */
	public List<UserDTO> findAllUsers() throws ServiceException {
		try {
			List<UserDTO> usersDTO = new ArrayList<UserDTO>();

			List<UserDAO> usersDAO = userRepository.findAll();
			if (usersDAO == null || usersDAO.isEmpty())
				throw new ServiceException("La liste des users est vide");

			for (UserDAO userDAO : usersDAO) {
				usersDTO.add(DAOToDTOConverter.userDAOToUserDTOWithoutCommands(userDAO));
			}

			return usersDTO;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "UserService->findAllUsers - ", e.getMessage(), e);
		}
	}

	/**
	 * @param userName
	 * @return
	 * @throws ServiceException
	 */
	public UserDAO findUserByUserName(String userName) throws ServiceException {
		try {
			if (userName == null || userName.isEmpty())
				throw new ServiceException("username ne peut pas être null ou vide.");

			UserDAO userDAO = userRepository.findByUsername(userName);
			if (userDAO == null)
				throw new ServiceException("Le user n'existe pas pour le username " + userName);
			return userDAO;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "UserService->findUserByUserName - ", e.getMessage(),
					e);
		}
	}

	/**
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public UserDAO save(UserDTO user) throws ServiceException {
		try {
			if (user == null || user.getUsername() == null || user.getUsername().isEmpty())
				throw new ServiceException("Le user ne peut pas être null ou sans username.");

			UserDAO userExist = userRepository.findByUsername(user.getUsername());
			if (userExist != null)
				throw new ServiceException("Le user existe déjà pour le username " + user.getUsername());
			if (user.getPassword() == null || user.getPassword().isEmpty())
				throw new ServiceException("Le password ne peut pas être vide ou null.");

			if (user.getLat() == 0.0f && user.getLng() == 0.0f)
				throw new ServiceException("La latitude et la longitude sont à 0.");

			UserDAO newUser = DTOToDAOConverter.userDTOToUserDAO(user);
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			return userRepository.save(newUser);

		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "UserService->save - ", e.getMessage(), e);
		}
	}

}
