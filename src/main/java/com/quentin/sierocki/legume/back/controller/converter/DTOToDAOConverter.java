package com.quentin.sierocki.legume.back.controller.converter;

import java.util.HashSet;

import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.CommandProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ProductDTO;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.domain.entity.CommandDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductTypeDAO;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;

public class DTOToDAOConverter {

	public static CommandDAO commandDTOToCommandDAO(CommandDTO commandDTO) throws ConvertionException {
		try {
			CommandDAO commandDAO = new CommandDAO();
			commandDAO.setId(commandDTO.getId());
			commandDAO.setPrice(commandDTO.getPrice());
			commandDAO.setQuantity(commandDTO.getQuantity());
			commandDAO.setCreationDate(commandDTO.getCreationDate());
			commandDAO.setCloseDate(commandDTO.getCloseDate());
			UserDAO userSeller = new UserDAO();
			userSeller.setId(commandDTO.getIdSeller());
			commandDAO.setSeller(userSeller);
			commandDAO.setCommandProducts(new HashSet<>());
			if (commandDTO.getCommandProducts() != null) {
				for (CommandProductDTO commProductDTO : commandDTO.getCommandProducts()) {
					commandDAO.getCommandProducts().add(commandProductDTOToCommandProductDAO(commProductDTO));
				}
			}
			return commandDAO;
		} catch (Exception e) {
			throw new ConvertionException("DTOToDAOConverter->commandDTOToCommandDAO - " , commandDTO.toString(), e);
		}
	}

	public static ProductDAO productDTOToProductDAO(ProductDTO productDTO) throws ConvertionException {
		try {
			ProductDAO productDAO = new ProductDAO();
			productDAO.setId(productDTO.getId());
			productDAO.setPrice(productDTO.getPrice());
			productDAO.setQuantity(productDTO.getId() == 0 ? productDTO.getInitialQuantity() : productDTO.getQuantity());
			productDAO.setInitialQuantity(productDTO.getInitialQuantity());
			productDAO.setProductType(new ProductTypeDAO(productDTO.getProductTypeName()));
			return productDAO;
		} catch (Exception e) {
			throw new ConvertionException("DTOToDAOConverter->productDTOToProductDAO - " , productDTO.toString(), e);
		}

	}

	public static UserDAO userDTOToUserDAO(UserDTO userDTO) throws ConvertionException {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.setId(userDTO.getId());
			userDAO.setUsername(userDTO.getUsername());
			userDAO.setPassword(userDTO.getPassword());
			userDAO.setLat(userDTO.getLat());
			userDAO.setLng(userDTO.getLng());
			userDAO.setAdress(userDTO.getAdress());
			userDAO.setCity(userDTO.getCity());
			userDAO.setEmail(userDTO.getEmail());
			userDAO.setPhone(userDTO.getPhone());
			return userDAO;
		} catch (Exception e) {
			throw new ConvertionException("DTOToDAOConverter->userDTOToUserDAO - " , userDTO.toString(), e);
		}

	}

	public static CommandProductDAO commandProductDTOToCommandProductDAO(CommandProductDTO commandProduct)
			throws ConvertionException {
		try {
			CommandProductDAO commandProductDAO = new CommandProductDAO();
			commandProductDAO.setPrice(commandProduct.getPrice());
			commandProductDAO.setQuantity(commandProduct.getQuantity());
			ProductDAO productDAO =new ProductDAO();
			productDAO.setId(commandProduct.getIdProduct());
			commandProductDAO.setProduct(productDAO);
			return commandProductDAO;
		} catch (Exception e) {
			throw new ConvertionException(
					"DTOToDAOConverter->commandProductDTOToCommandProductDAO - " , commandProduct.toString(), e);
		}

	}

}
