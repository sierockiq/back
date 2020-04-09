package com.quentin.sierocki.service.converter;

import java.util.HashSet;

import javax.transaction.Transactional;

import com.quentin.sierocki.controller.model.CommandDTO;
import com.quentin.sierocki.controller.model.CommandProductDTO;
import com.quentin.sierocki.controller.model.ProductDTO;
import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.CommandDAO;
import com.quentin.sierocki.domain.entity.CommandProductDAO;
import com.quentin.sierocki.domain.entity.ProductDAO;
import com.quentin.sierocki.domain.entity.ProductStatus;
import com.quentin.sierocki.domain.entity.UserDAO;

public class DAOToDTOConverter {

	public static CommandDTO commandDAOToCommandDTO(CommandDAO commandDAO) throws ConvertionException{;
		try {
			CommandDTO commandDTO = new CommandDTO();
			commandDTO.setId(commandDAO.getId());
			commandDTO.setPrice(commandDAO.getPrice());
			commandDTO.setQuantite(commandDAO.getQuantity());
			commandDTO.setDateCommande(commandDAO.getDateCommande());
			commandDTO.setCommandProducts(new HashSet<CommandProductDTO>());
			if (commandDAO.getCommandProducts() != null) {
				for(CommandProductDAO commProductDAO:commandDAO.getCommandProducts() ) {
					commandDTO.getCommandProducts().add(commandProductDAOToCommandProductDTO(commProductDAO));
				}
			}
			commandDTO.setIdSeller(String.valueOf(commandDAO.getSeller().getId()));
			return commandDTO;
		}catch(Exception e) {
			throw new ConvertionException("DAOToDTOConverter.commandDAOToCommandDTO " + commandDAO.toString(),e);
		}
	}

	private static CommandProductDTO commandProductDAOToCommandProductDTO(CommandProductDAO commProductDAO) throws ConvertionException{
		try {
			CommandProductDTO commandProductDTO = new CommandProductDTO();
			commandProductDTO.setId(commProductDAO.getId());
			commandProductDTO.setPrice(commProductDAO.getPrice());
			commandProductDTO.setQuantity(commProductDAO.getQuantity());
			commandProductDTO.setIdProduct(String.valueOf(commProductDAO.getCommand().getId()));
			return commandProductDTO;
		}catch(Exception e) {
			throw new ConvertionException("Problème de convertion commandProductDAOToCommandProductDTO" + commProductDAO.toString(),e);
		}	
	}

	public static ProductDTO productDAOToProductDTO(ProductDAO productDAO) throws ConvertionException {
		try {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setId(productDAO.getId());
			productDTO.setPrice(productDAO.getPrice());
			productDTO.setQuantity(productDAO.getQuantity());
			productDTO.setInitialQuantity(productDAO.getInitialQuantity());
			productDTO.setProductTypeName(productDAO.getProductType().getName());
			productDTO.setStatus(productDAO.getStatus().name());
			return productDTO;
		}catch(Exception e) {
			throw new ConvertionException("Problème de convertion productDAOToProductDTO" + productDAO.toString(),e);
		}	
	}

	public static UserDTO userDAOToUserDTO(UserDAO userDAO) throws ConvertionException{
		try {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(userDAO.getId());
			userDTO.setUsername(userDAO.getUsername());
			userDTO.setPassword(userDAO.getPassword());
			userDTO.setLat(userDAO.getLat());
			userDTO.setLng(userDAO.getLng());
			userDTO.setAdress(userDAO.getAdress());
			userDTO.setCity(userDAO.getCity());
			userDTO.setEmail(userDAO.getEmail());
			userDTO.setPhone(userDAO.getPhone());
			userDTO.setProducts(new HashSet<ProductDTO>());
			if (userDAO.getProducts() != null) {
				for(ProductDAO productDAO : userDAO.getProducts()) {
					if (((ProductDAO) productDAO).getStatus().equals(ProductStatus.AVAILABLE)) {
						userDTO.getProducts().add(productDAOToProductDTO(productDAO));
					}
				}
			}
			return userDTO;
		}catch(Exception e) {
			throw new ConvertionException("Problème de convertion productDAOToProductDTO" + userDAO.toString(),e);
		}	
		
		
	}

	public static UserDTO userDAOToUserDTOWithoutCommands(UserDAO userDAO) throws ConvertionException {
		try {
			UserDTO userDTO = userDAOToUserDTOWithoutCommands(userDAO);
			userDTO.setPurchases(new HashSet<CommandDTO>());
			if (userDAO.getPurchases() != null) {
				for(CommandDAO commDAO : userDAO.getPurchases()) {
					userDTO.getPurchases().add(commandDAOToCommandDTO(commDAO));
				}
			}
			userDTO.setSales(new HashSet<CommandDTO>());
			if (userDAO.getSales() != null) {
				for(CommandDAO commDAO : userDAO.getSales()) {
					userDTO.getSales().add(commandDAOToCommandDTO(commDAO));
				}
			}
			return userDTO;
		}catch(Exception e) {
			throw new ConvertionException("Problème de convertion productDAOToProductDTO" + userDAO.toString(),e);
		}	
		
		
	}
}
