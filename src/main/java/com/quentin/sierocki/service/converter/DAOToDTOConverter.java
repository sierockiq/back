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

	public static CommandDTO commandDAOToCommandDTO(CommandDAO commandDAO) {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setId(commandDAO.getId());
		commandDTO.setPrice(commandDAO.getPrice());
		commandDTO.setQuantite(commandDAO.getQuantity());
		commandDTO.setDateCommande(commandDAO.getDateCommande());
		commandDTO.setCommandProducts(new HashSet<CommandProductDTO>());
		if (commandDAO.getCommandProducts() != null) {
			commandDAO.getCommandProducts().forEach((commProductDAO) -> commandDTO.getCommandProducts()
					.add(commandProductDAOToCommandProductDTO(commProductDAO)));
		}
		commandDTO.setIdSeller(String.valueOf(commandDAO.getSeller().getId()));
		return commandDTO;
	}

	private static CommandProductDTO commandProductDAOToCommandProductDTO(CommandProductDAO commProductDAO) {
		CommandProductDTO commandProductDTO = new CommandProductDTO();
		commandProductDTO.setId(commProductDAO.getId());
		commandProductDTO.setPrice(commProductDAO.getPrice());
		commandProductDTO.setQuantity(commProductDAO.getQuantity());
		commandProductDTO.setIdProduct(String.valueOf(commProductDAO.getCommand().getId()));
		return commandProductDTO;
	}

	public static ProductDTO productDAOToProductDTO(ProductDAO productDAO) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productDAO.getId());
		productDTO.setPrice(productDAO.getPrice());
		productDTO.setQuantity(productDAO.getQuantity());
		productDTO.setInitialQuantity(productDAO.getInitialQuantity());
		productDTO.setProductTypeName(productDAO.getProductType().getName());
		productDTO.setStatus(productDAO.getStatus().name());
		return productDTO;
	}

	public static UserDTO userDAOToUserDTO(UserDAO userDAO) {
		UserDTO userDTO = userDAOToUserDTOWithoutCommands(userDAO);
		userDTO.setPurchases(new HashSet<CommandDTO>());
		if (userDAO.getPurchases() != null) {
			userDAO.getPurchases().forEach((commDAO) -> userDTO.getPurchases().add(commandDAOToCommandDTO(commDAO)));
		}
		userDTO.setSales(new HashSet<CommandDTO>());
		if (userDAO.getSales() != null) {
			userDAO.getSales().forEach((commDAO) -> userDTO.getSales().add(commandDAOToCommandDTO(commDAO)));
		}
		return userDTO;
	}

	public static UserDTO userDAOToUserDTOWithoutCommands(UserDAO userDAO) {
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
			userDAO.getProducts().forEach((prodDAO) -> {
				if (((ProductDAO) prodDAO).getStatus().equals(ProductStatus.AVAILABLE)) {
					userDTO.getProducts().add(productDAOToProductDTO(prodDAO));
				}
			});
		}
		return userDTO;
	}
}
