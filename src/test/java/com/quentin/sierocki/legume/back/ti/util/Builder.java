package com.quentin.sierocki.legume.back.ti.util;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.CommandProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ProductDTO;
import com.quentin.sierocki.legume.back.controller.model.UserDTO;

public class Builder {
	
	public static UserDTO createUser() {
		UserDTO userDTO = new UserDTO();
		userDTO.setAdress("1 rue de la cité");
		userDTO.setCity("Londres");
		userDTO.setEmail("quentin.sierocki@gmail.com");
		userDTO.setLat(1.2563f);
		userDTO.setLng(1.2568f);
		userDTO.setPhone("0985658956");
		userDTO.setPassword("test");
		userDTO.setUsername("quentin");
		return userDTO;
	}
	
	
	public static UserDTO createOtherUser() {
		UserDTO userDTO = new UserDTO();
		userDTO.setAdress("3 rue de la cité");
		userDTO.setCity("Londres");
		userDTO.setEmail("marco.sierocki@gmail.com");
		userDTO.setLat(1.2563f);
		userDTO.setLng(1.2568f);
		userDTO.setPhone("0985658956");
		userDTO.setPassword("test");
		userDTO.setUsername("marco");
		return userDTO;
	}
	
	
	
	public static ProductDTO createProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setInitialQuantity(50);
		productDTO.setQuantity(50);
		productDTO.setProductTypeName("tomate");
		productDTO.setPrice(2);
		return productDTO;
	}
	
	public static CommandDTO createCommand() {
		CommandDTO commandDTO = new CommandDTO();
		commandDTO.setQuantity(6);
		commandDTO.setPrice(4);
		LocalDateTime localDateTime=LocalDateTime.now();
		commandDTO.setCreationDate(localDateTime);
		commandDTO.setCommandProducts(new ArrayList<>());
		commandDTO.getCommandProducts().add(createCommandProductDTO());
		commandDTO.getCommandProducts().add(createCommandProductDTO());
		return commandDTO;
	}
	
	public static CommandProductDTO createCommandProductDTO() {
		CommandProductDTO cmdProductDTO = new CommandProductDTO();
		cmdProductDTO.setPrice(2);
		cmdProductDTO.setQuantity(3);
		return cmdProductDTO;
		
	}
	
	

	
}
