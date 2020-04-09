package com.quentin.sierocki.service;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.controller.model.CommandDTO;
import com.quentin.sierocki.controller.model.CommandProductDTO;
import com.quentin.sierocki.domain.entity.CommandDAO;
import com.quentin.sierocki.domain.entity.CommandProductDAO;
import com.quentin.sierocki.domain.entity.CommandStatus;
import com.quentin.sierocki.domain.entity.ProductDAO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.domain.repository.CommandProductRepository;
import com.quentin.sierocki.domain.repository.CommandRepository;
import com.quentin.sierocki.domain.repository.ProductRepository;
import com.quentin.sierocki.domain.repository.UserRepository;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.converter.ConvertionException;
import com.quentin.sierocki.service.converter.DAOToDTOConverter;
import com.quentin.sierocki.service.converter.DTOToDAOConverter;

@Service
public class CommandService {

	@Autowired
	private CommandRepository commandRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CommandProductRepository commandProductRepository;

	public CommandDTO save(String idUser, CommandDTO command) throws FunctionnalException, ConvertionException {
		UserDAO userBuyer = userRepository.findById(Integer.valueOf(idUser)).orElse(null);

		if (userBuyer == null)
			throw new FunctionnalException("user id :" + idUser);

		UserDAO userSeller = userRepository.findById(Integer.valueOf(command.getIdSeller())).orElse(null);
		if (userSeller == null)
			throw new FunctionnalException("produit id :" + command.getIdSeller());

		CommandDAO commandDAO = commandRepository.save(new CommandDAO());
		commandDAO.setBuyer(userBuyer);
		commandDAO.setSeller(userSeller);
		commandDAO.setCommandProducts(new HashSet<CommandProductDAO>());
		float price = 0, quantity = 0;
		for (CommandProductDTO commandProduct : command.getCommandProducts()) {
			ProductDAO productDAO = productRepository.findById(Integer.valueOf(commandProduct.getIdProduct()))
					.orElse(null);
			if (productDAO == null || productDAO.getFarmer() == null || productDAO.getFarmer().getId() == 0
					|| userSeller.getId() == 0 || productDAO.getFarmer().getId() != userSeller.getId())
				throw new FunctionnalException("Le produit n'appartient pas au bon vendeur ou n'existe pas. id :"
						+ commandProduct.getIdProduct());

			if (commandProduct.getQuantity() > productDAO.getQuantity())
				throw new FunctionnalException("La quantité commandée est trop importante.");

			CommandProductDAO commandProductdao = DTOToDAOConverter
					.commandProductDTOToCommandProductDAO(commandProduct);
			commandProductdao.setProduct(productDAO);
			commandProductdao.setCommand(commandDAO);
			commandDAO.getCommandProducts().add(commandProductdao);
			price = price + commandProduct.getPrice();
			quantity = quantity + commandProduct.getQuantity();
			productDAO.setQuantity(productDAO.getQuantity() - commandProduct.getQuantity());
			productRepository.save(productDAO);
			commandProductRepository.save(commandProductdao);
		}
		commandDAO.setQuantity(quantity);
		commandDAO.setPrice(price);
		commandDAO.setStatus(CommandStatus.IN_PROGRESS);
		CommandDAO commandSaved = commandRepository.save(commandDAO);
		return DAOToDTOConverter.commandDAOToCommandDTO(commandSaved);
	}

}
