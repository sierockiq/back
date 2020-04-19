package com.quentin.sierocki.legume.back.ti.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.quentin.sierocki.legume.back.controller.CommandController;
import com.quentin.sierocki.legume.back.controller.ControllerException;
import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.converter.DTOToDAOConverter;
import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.CommandProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.domain.entity.CommandStatus;
import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.service.CommandService;
import com.quentin.sierocki.legume.back.service.ProductService;
import com.quentin.sierocki.legume.back.service.ServiceException;
import com.quentin.sierocki.legume.back.service.UserService;
import com.quentin.sierocki.legume.back.ti.SpringBootRestApplicationTest;
import com.quentin.sierocki.legume.back.ti.util.Builder;

public class TestCommandController extends SpringBootRestApplicationTest {

	@Autowired
	CommandController commandController;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	CommandService commandService;

	UserDAO userDAO1 = null, userDAO2 = null;
	ProductDAO productDAOUser1 = null;
	CommandDTO commandDTO = null;

	@Before
	public void prepareContextSave() {
		super.prepareContext();
		try {
			userDAO1 = userService.save(Builder.createUser());
			productDAOUser1 = productService.addNewProduct(userDAO1.getId(),
					DTOToDAOConverter.productDTOToProductDAO(Builder.createProduct()));
			userDAO2 = userService.save(Builder.createOtherUser());
			commandDTO = Builder.createCommand();
			commandDTO.setIdSeller(userDAO1.getId());
			for (CommandProductDTO cmdProdDTO : commandDTO.getCommandProducts()) {
				cmdProdDTO.setIdProduct(productDAOUser1.getId());
			}
		} catch (Exception e) {
			logger.error("erreur initialisation context product.");
		}
	}

	// method save test with all bad element
	@Test(expected = ControllerException.class)
	public void postCommand_ko_id_seller() throws ControllerException {
		commandDTO.setIdSeller(0);
		commandController.postCommand(userDAO2.getId(), commandDTO);
	}

	@Test(expected = ControllerException.class)
	public void postCommand_ko_price() throws ControllerException {
		commandDTO.setPrice(0);
		commandController.postCommand(userDAO2.getId(), commandDTO);
	}

	@Test(expected = ControllerException.class)
	public void postCommand_ko_quantity() throws ControllerException {
		commandDTO.setQuantity(0);
		commandController.postCommand(userDAO2.getId(), commandDTO);
	}

	@Test
	public void postCommand_ok() throws ServiceException, ControllerException {
		CommandDTO cmd = commandController.postCommand(userDAO2.getId(), commandDTO);
		ProductDAO prodAfter = productService.findProductById(productDAOUser1.getId());
		Assert.assertNull(cmd.getCloseDate());
		Assert.assertEquals(cmd.getPrice(), commandDTO.getPrice(), 0.01);
		Assert.assertEquals(cmd.getQuantity(), commandDTO.getQuantity(), 0.01);
		Assert.assertEquals(cmd.getStatus(), CommandStatus.IN_PROGRESS.name());
		Assert.assertEquals(cmd.getIdSeller(), commandDTO.getIdSeller());
		for (CommandProductDTO cmdProdDTO : cmd.getCommandProducts()) {
			Assert.assertEquals(cmdProdDTO.getPrice(), commandDTO.getPrice() / 2, 0.01);
			Assert.assertEquals(cmdProdDTO.getQuantity(), commandDTO.getQuantity() / 2, 0.01);
			Assert.assertEquals(cmdProdDTO.getStatus(), CommandStatus.IN_PROGRESS.name());
			Assert.assertEquals(cmdProdDTO.getIdProduct(), productDAOUser1.getId());
		}
		Assert.assertEquals(prodAfter.getInitialQuantity(), productDAOUser1.getInitialQuantity(), 0.01);
		Assert.assertEquals(prodAfter.getPrice(), productDAOUser1.getPrice(), 0.01);
		Assert.assertEquals(prodAfter.getQuantity(), productDAOUser1.getQuantity() - commandDTO.getQuantity(), 0.01);
	}

	@Test
	public void cancelCommand_ok()
			throws ServiceException, ConvertionException, ValidationException, ControllerException {
		CommandDTO cmd = commandController.postCommand(userDAO2.getId(), commandDTO);
		cmd = commandController.cancelCommand(userDAO2.getId(), cmd.getId(), CommandStatus.CANCELED.name());
		ProductDAO prodAfter = productService.findProductById(productDAOUser1.getId());

		Assert.assertNotNull(cmd.getCloseDate());
		Assert.assertEquals(cmd.getPrice(), commandDTO.getPrice(), 0.01);
		Assert.assertEquals(cmd.getQuantity(), commandDTO.getQuantity(), 0.01);
		Assert.assertEquals(cmd.getStatus(), CommandStatus.CANCELED.name());
		Assert.assertEquals(cmd.getIdSeller(), commandDTO.getIdSeller());
		for (CommandProductDTO cmdProdDTO : cmd.getCommandProducts()) {
			Assert.assertEquals(cmdProdDTO.getPrice(), commandDTO.getPrice() / 2, 0.01);
			Assert.assertEquals(cmdProdDTO.getQuantity(), commandDTO.getQuantity() / 2, 0.01);
			Assert.assertEquals(cmdProdDTO.getStatus(), CommandStatus.CANCELED.name());
			Assert.assertEquals(cmdProdDTO.getIdProduct(), productDAOUser1.getId());
		}
		Assert.assertEquals(prodAfter.getInitialQuantity(), productDAOUser1.getInitialQuantity(), 0.01);
		Assert.assertEquals(prodAfter.getPrice(), productDAOUser1.getPrice(), 0.01);
		Assert.assertEquals(prodAfter.getQuantity(), productDAOUser1.getQuantity(), 0.01);
	}

}
