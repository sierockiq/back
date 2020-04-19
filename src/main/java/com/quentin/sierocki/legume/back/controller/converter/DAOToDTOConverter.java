package com.quentin.sierocki.legume.back.controller.converter;

import java.util.ArrayList;
import java.util.HashSet;

import javax.transaction.Transactional;

import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.CommandProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ProductDTO;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;
import com.quentin.sierocki.legume.back.domain.entity.CommandDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductStatus;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;

public class DAOToDTOConverter {

	@Transactional
	public static CommandDTO commandDAOToCommandDTO(CommandDAO commandDAO) throws ConvertionException{;
		try {
			CommandDTO commandDTO = new CommandDTO();
			commandDTO.setId(commandDAO.getId());
			commandDTO.setPrice(commandDAO.getPrice());
			commandDTO.setQuantity(commandDAO.getQuantity());
			commandDTO.setCloseDate(commandDAO.getCloseDate());
			commandDTO.setStatus(commandDAO.getStatus().name());
			commandDTO.setCreationDate(commandDAO.getCreationDate());
			commandDTO.setCommandProducts(new ArrayList<CommandProductDTO>());
			if (commandDAO.getCommandProducts() != null) {
				for(CommandProductDAO commProductDAO:commandDAO.getCommandProducts() ) {
					commandDTO.getCommandProducts().add(commandProductDAOToCommandProductDTO(commProductDAO));
				}
			}
			commandDTO.setIdSeller(commandDAO.getSeller().getId());
			return commandDTO;
		}catch(Exception e) {
			throw new ConvertionException("DAOToDTOConverter->commandDAOToCommandDTO - " , commandDAO.toString(),e);
		}
	}

	@Transactional
	private static CommandProductDTO commandProductDAOToCommandProductDTO(CommandProductDAO commProductDAO) throws ConvertionException{
		try {
			CommandProductDTO commandProductDTO = new CommandProductDTO();
			commandProductDTO.setId(commProductDAO.getId());
			commandProductDTO.setPrice(commProductDAO.getPrice());
			commandProductDTO.setQuantity(commProductDAO.getQuantity());
			commandProductDTO.setIdProduct(commProductDAO.getProduct().getId());
			commandProductDTO.setStatus(commProductDAO.getStatus().name());
			return commandProductDTO;
		}catch(Exception e) {
			throw new ConvertionException("DAOToDTOConverter->commandProductDAOToCommandProductDTO - " , commProductDAO.toString(),e);
		}	
	}

	@Transactional
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
			throw new ConvertionException("DAOToDTOConverter->productDAOToProductDTO - " , productDAO.toString(),e);
		}	
	}

	@Transactional
	public static UserDTO userDAOToUserDTOWithoutCommands(UserDAO userDAO) throws ConvertionException{
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
			userDTO.setProducts(new ArrayList<>());
			if (userDAO.getProducts() != null) {
				for(ProductDAO productDAO : userDAO.getProducts()) {
					if ( ProductStatus.AVAILABLE.equals(productDAO.getStatus())) {
						userDTO.getProducts().add(productDAOToProductDTO(productDAO));
					}
				}
			}
			return userDTO;
		}catch(Exception e) {
			throw new ConvertionException("DAOToDTOConverter->userDAOToUserDTOWithoutCommands - " , userDAO.toString(),e);
		}	
		
		
	}

	@Transactional
	public static UserDTO userDAOToUserDTO(UserDAO userDAO) throws ConvertionException {
		try {
			UserDTO userDTO = userDAOToUserDTOWithoutCommands(userDAO);
			userDTO.setPurchases(new ArrayList<>());
			if (userDAO.getPurchases() != null) {
				for(CommandDAO commDAO : userDAO.getPurchases()) {
					userDTO.getPurchases().add(commandDAOToCommandDTO(commDAO));
				}
			}
			userDTO.setSales(new ArrayList<>());
			if (userDAO.getSales() != null) {
				for(CommandDAO commDAO : userDAO.getSales()) {
					userDTO.getSales().add(commandDAOToCommandDTO(commDAO));
				}
			}
			return userDTO;
		}catch(Exception e) {
			throw new ConvertionException("DAOToDTOConverter->userDAOToUserDTO - " , userDAO.toString(),e);
		}	
		
		
	}
}
