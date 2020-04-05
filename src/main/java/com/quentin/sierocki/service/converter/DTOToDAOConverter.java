package com.quentin.sierocki.service.converter;

import java.sql.Timestamp;
import java.util.Date;

import com.quentin.sierocki.controller.model.CommandDTO;
import com.quentin.sierocki.controller.model.CommandProductDTO;
import com.quentin.sierocki.controller.model.ProductDTO;
import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.CommandDAO;
import com.quentin.sierocki.domain.entity.CommandProductDAO;
import com.quentin.sierocki.domain.entity.ProductDAO;
import com.quentin.sierocki.domain.entity.UserDAO;

public class DTOToDAOConverter {

	public static CommandDAO commandDTOToCommandDAO(CommandDTO commandDTO) {
		CommandDAO commandDAO = new CommandDAO();
		commandDAO.setId(commandDTO.getId());
		commandDAO.setPrice(commandDTO.getPrice());
		commandDAO.setQuantity(commandDTO.getQuantity());
		commandDAO.setDateCommande(commandDTO.getDateCommande() == null ? new Timestamp(new Date().getTime())
				: commandDTO.getDateCommande());
		return commandDAO;
	}

	public static ProductDAO productDTOToProductDAO(ProductDTO productDTO) {
		ProductDAO productDAO = new ProductDAO();
		productDAO.setId(productDTO.getId());
		productDAO.setPrice(productDTO.getPrice());
		productDAO.setQuantity(productDTO.getId() == 0 ? productDTO.getInitialQuantity() : productDTO.getQuantity());
		productDAO.setInitialQuantity(productDTO.getInitialQuantity());
		return productDAO;
	}

	public static UserDAO userDTOToUserDAO(UserDTO userDTO) {
		UserDAO userDAO = new UserDAO();
		userDAO.setId(Integer.valueOf(userDTO.getId()));
		userDAO.setUsername(userDTO.getUsername());
		userDAO.setPassword(userDTO.getPassword());
		userDAO.setLat(userDTO.getLat());
		userDAO.setLng(userDTO.getLng());
		userDAO.setAdress(userDTO.getAdress());
		userDAO.setCity(userDTO.getCity());
		userDAO.setEmail(userDTO.getEmail());
		userDAO.setPhone(userDTO.getPhone());
		return userDAO;
	}

	public static CommandProductDAO commandProductDTOToCommandProductDAO(CommandProductDTO commandProduct) {
		CommandProductDAO commandProductDAO = new CommandProductDAO();
		commandProductDAO.setPrice(commandProduct.getPrice());
		commandProductDAO.setQuantity(commandProduct.getQuantity());
		return commandProductDAO;
	}
	
	

}
