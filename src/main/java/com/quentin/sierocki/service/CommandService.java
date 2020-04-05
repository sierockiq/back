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

	public CommandDTO save(String idUser, CommandDTO command) throws FunctionnalException {
		Optional<UserDAO> userExist = userRepository.findById(Integer.valueOf(idUser));
		if (!userExist.isPresent()) {
			throw new FunctionnalException("user id :" + idUser);
		}

		UserDAO userBuyer = userExist.get();

		Optional<UserDAO> userSellerExist = userRepository.findById(Integer.valueOf(command.getIdSeller()));
		if (!userSellerExist.isPresent()) {
			throw new FunctionnalException("produit id :" + command.getIdSeller());
		}

		UserDAO userSeller = userSellerExist.get();

		CommandDAO commandDAO = commandRepository.save(new CommandDAO());
		commandDAO.setBuyer(userBuyer);
		commandDAO.setSeller(userSeller);
		commandDAO.setCommandProducts(new HashSet<CommandProductDAO>());
		float price=0,quantity=0;
		for (CommandProductDTO commandProduct : command.getCommandProducts()) {
			Optional<ProductDAO> productExist = productRepository.findById(Integer.valueOf(commandProduct.getIdProduct()));
			if (!userSellerExist.isPresent()) {
				throw new FunctionnalException("produit id :" + command.getIdSeller());
			} else {
				ProductDAO productDAO = productExist.get();
				if (productDAO.getFarmer().getId() != userSeller.getId()) {
					throw new FunctionnalException("Le produit n'appartient pas au bon vendeur.");
				} else {
					if (commandProduct.getQuantity() < productDAO.getQuantity()) {
						CommandProductDAO commandProductdao = DTOToDAOConverter.commandProductDTOToCommandProductDAO(commandProduct);
						commandProductdao.setProduct(productDAO);
						commandProductdao.setCommand(commandDAO);
						commandDAO.getCommandProducts().add(commandProductdao);
						price=price+commandProduct.getPrice();
						quantity=quantity+commandProduct.getQuantity();
						productDAO.setQuantity(productDAO.getQuantity()-commandProduct.getQuantity());
						productRepository.save(productDAO);
						commandProductRepository.save(commandProductdao);
					} else {
						throw new FunctionnalException("La quantité commandée est trop importante.");
					}
				}
			}
		}
		commandDAO.setQuantity(quantity);
		commandDAO.setPrice(price);
		commandDAO.setStatus(CommandStatus.IN_PROGRESS);
		CommandDAO commandSaved = commandRepository.save(commandDAO);
		return DAOToDTOConverter.commandDAOToCommandDTO(commandSaved);

	}

}
