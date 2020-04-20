package com.quentin.sierocki.legume.back.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.legume.back.domain.entity.CommandDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandStatus;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.domain.repository.CommandProductRepository;
import com.quentin.sierocki.legume.back.domain.repository.CommandRepository;
import com.quentin.sierocki.legume.back.globals.Constants;

@Service
public class CommandService {

	@Autowired
	private CommandRepository commandRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CommandProductRepository commandProductRepository;

	/**
	 * @param idUser
	 * @param command
	 * @return
	 * @throws ServiceException
	 */
	public CommandDAO save(long idUser, CommandDAO command) throws ServiceException {
		try {
			UserDAO userBuyer = userService.findUserById(idUser);
			if (command == null || command.getSeller() == null || command.getSeller().getId() == 0)
				throw new ServiceException("La commande n'a pas de vendeur avec un id.");

			UserDAO userSeller = userService.findUserById(command.getSeller().getId());

			LocalDateTime localDateTime = LocalDateTime.now();
			command.setBuyer(userBuyer);
			command.setSeller(userSeller);
			for (CommandProductDAO commandProductDAO : command.getCommandProducts()) {
				if (commandProductDAO == null || commandProductDAO.getProduct() == null
						|| commandProductDAO.getProduct().getId() == 0)
					throw new ServiceException("Le produit commandé n'est pas présent.");
				productService.updateProductQuantityWhenUserMakeCommand(commandProductDAO.getProduct().getId(),
						commandProductDAO.getQuantity(), userSeller.getId());
				commandProductDAO.setStatus(CommandStatus.IN_PROGRESS);
				commandProductDAO.setCommand(command);
			}
			command.setCreationDate(localDateTime);
			command.setStatus(CommandStatus.IN_PROGRESS);
			CommandDAO commandSaved = commandRepository.save(command);
			return commandSaved;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "CommandService->save - ", e.getMessage(), e);
		}
	}

	/**
	 * Annule la commande : status CANCEL pour toutes les commandes et ajout de la
	 * quantité des produits commandés à la quantité disponible du produit
	 * sous-commandes
	 * 
	 * @param commandDTO
	 * @return
	 * @throws ServiceException
	 */
	public CommandDAO cancelCommand(long idCommand, String status) throws ServiceException {
		try {
			CommandStatus commandStatus = CommandStatus.getStatusOrNull(status);
			CommandDAO commandDAO = findCommandById(idCommand);
			commandDAO.setStatus(commandStatus);

			if (commandDAO.getCommandProducts() != null) {
				for (CommandProductDAO commandProductDAO : commandDAO.getCommandProducts()) {
					commandProductDAO.setStatus(commandStatus);
					productService.updateProductQuantityWhenCommandIsCancelled(commandProductDAO.getProduct().getId(),
							commandProductDAO.getQuantity());
					commandProductDAO.setCloseDate(LocalDateTime.now());
					commandProductRepository.save(commandProductDAO);
				}
			}
			commandDAO.setCloseDate(LocalDateTime.now());
			CommandDAO commandSaved = commandRepository.save(commandDAO);
			return commandSaved;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "CommandService->updateCommand - ", e.getMessage(), e);
		}
	}

	public void changeStatusByProductId(long idProduct, CommandStatus commandStatus) {

		List<CommandProductDAO> listCommandProduct = commandProductRepository.findByProduct_Id(idProduct);
		if (listCommandProduct != null) {
			for (CommandProductDAO commandProductDAO : listCommandProduct) {
				commandProductDAO.setStatus(CommandStatus.CANCELED);
				commandProductDAO.setCloseDate(LocalDateTime.now());
			}
		}
		commandProductRepository.saveAll(listCommandProduct);

	}

	public CommandDAO findCommandById(long idCommand) throws ServiceException {
		try {
			CommandDAO commandDAO = commandRepository.findById(idCommand).orElse(null);
			if (commandDAO == null)
				throw new ServiceException("La commande n'existe pas avec l'id " + idCommand);

			return commandDAO;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->findCommandById - ", e.getMessage(),
					e);
		}
	}

}
