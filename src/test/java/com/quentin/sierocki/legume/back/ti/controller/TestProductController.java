package com.quentin.sierocki.legume.back.ti.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.quentin.sierocki.legume.back.controller.ControllerException;
import com.quentin.sierocki.legume.back.controller.ProductController;
import com.quentin.sierocki.legume.back.controller.converter.DTOToDAOConverter;
import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.CommandProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ProductDTO;
import com.quentin.sierocki.legume.back.domain.entity.CommandDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.CommandStatus;
import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductStatus;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.service.CommandService;
import com.quentin.sierocki.legume.back.service.ProductService;
import com.quentin.sierocki.legume.back.service.ServiceException;
import com.quentin.sierocki.legume.back.service.UserService;
import com.quentin.sierocki.legume.back.ti.SpringBootRestApplicationTest;
import com.quentin.sierocki.legume.back.ti.util.Builder;

public class TestProductController extends SpringBootRestApplicationTest {

	@Autowired
	UserService userService;

	UserDAO userDAO1 = null, userDAO2 = null;
	ProductDAO productDAOUser1 = null;
	CommandDAO commandDAO = null;

	@Autowired
	ProductService productService;

	@Autowired
	CommandService commandService;

	@Autowired
	ProductController productController;

	@Before
	public void prepareContextSave() {
		try {
			userDAO1 = userService.save(Builder.createUser());
		} catch (Exception e) {
			logger.error("erreur initialisation context product.");
		}
	}

	public void prepareContextCancel() {
		try {
			productDAOUser1 = productService.addNewProduct(userDAO1.getId(),
					DTOToDAOConverter.productDTOToProductDAO(Builder.createProduct()));
		} catch (Exception e) {
			logger.error("erreur initialisation context product.", e);
		}
	}

	public void prepareContextCommand() {
		try {
			userDAO2 = userService.save(Builder.createOtherUser());
			CommandDTO commandDTO = Builder.createCommand();
			commandDTO.setIdSeller(userDAO1.getId());
			for (CommandProductDTO cmdProdDTO : commandDTO.getCommandProducts()) {
				cmdProdDTO.setIdProduct(productDAOUser1.getId());
			}
			commandDAO = commandService.save(userDAO2.getId(), DTOToDAOConverter.commandDTOToCommandDAO(commandDTO));
		} catch (Exception e) {
			logger.error("erreur initialisation context command.", e);
		}
	}

	// method save test with all bad element
	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_product_initial_quantity() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setInitialQuantity(0);
		productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.fail();
	}

	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_product_price() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setPrice(0);
		productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.fail();
	}

	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_product_quantity_sup_initial_quantity() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setQuantity(250000);
		productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.fail();
	}

	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_product_type_name_empty() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setProductTypeName("");
		productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.fail();
	}

	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_product_type_name_null() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setProductTypeName(null);
		productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.fail();
	}

	@Test(expected = ControllerException.class)
	public void addNewProduct_bad_id_user() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productController.addNewProduct(1, productDTO);
		Assert.fail();
	}

	@Test
	public void addNewProduct_OK_with_quantity() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		ProductDTO prodRet = productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.assertEquals(productDTO.getInitialQuantity(), prodRet.getInitialQuantity(), 0.01);
		Assert.assertEquals(productDTO.getQuantity(), prodRet.getQuantity(), 0.01);
		Assert.assertEquals(productDTO.getPrice(), prodRet.getPrice(), 0.01);
		Assert.assertEquals(productDTO.getProductTypeName(), prodRet.getProductTypeName());
		Assert.assertEquals(prodRet.getStatus(), ProductStatus.AVAILABLE.name());
	}

	@Test
	public void addNewProduct_OK_without_quantity() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setQuantity(0);
		ProductDTO prodRet = productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.assertEquals(productDTO.getInitialQuantity(), prodRet.getInitialQuantity(), 0.01);
		Assert.assertEquals(productDTO.getInitialQuantity(), prodRet.getQuantity(), 0.01);
		Assert.assertEquals(productDTO.getPrice(), prodRet.getPrice(), 0.01);
		Assert.assertEquals(productDTO.getProductTypeName(), prodRet.getProductTypeName());
		Assert.assertEquals(prodRet.getStatus(), ProductStatus.AVAILABLE.name());
	}

	@Test
	public void addNewProduct_OK_with_bad_status() throws ControllerException {
		ProductDTO productDTO = Builder.createProduct();
		productDTO.setStatus(ProductStatus.UNAVAILABLE.name());
		ProductDTO prodRet = productController.addNewProduct(userDAO1.getId(), productDTO);
		Assert.assertEquals(productDTO.getInitialQuantity(), prodRet.getInitialQuantity(), 0.01);
		Assert.assertEquals(productDTO.getInitialQuantity(), prodRet.getQuantity(), 0.01);
		Assert.assertEquals(productDTO.getPrice(), prodRet.getPrice(), 0.01);
		Assert.assertEquals(productDTO.getProductTypeName(), prodRet.getProductTypeName());
		Assert.assertEquals(prodRet.getStatus(), ProductStatus.AVAILABLE.name());
	}
	// method cancelProduct

	@Test(expected = ControllerException.class)
	public void cancelProduct_ko_bad_id_product() throws ControllerException {
		prepareContextCancel();
		productController.cancelProduct(userDAO1.getId(),0);
	}

	@Test
	public void cancelProduct_ok_without_command() throws ServiceException, ControllerException {
		prepareContextCancel();
		productController.cancelProduct(userDAO1.getId(), productDAOUser1.getId());
		ProductDAO prodRet = productService.findProductById(productDAOUser1.getId());
		Assert.assertEquals(productDAOUser1.getInitialQuantity(), prodRet.getInitialQuantity(), 0.01);
		Assert.assertEquals(productDAOUser1.getInitialQuantity(), prodRet.getQuantity(), 0.01);
		Assert.assertEquals(productDAOUser1.getPrice(), prodRet.getPrice(), 0.01);
		Assert.assertEquals(productDAOUser1.getProductType().getName(), prodRet.getProductType().getName());
		Assert.assertEquals(prodRet.getStatus().name(), ProductStatus.UNAVAILABLE.name());
	}

	@Test
	public void cancelProduct_ok_with_command() throws ServiceException, ControllerException {
		prepareContextCancel();
		prepareContextCommand();
		productController.cancelProduct(userDAO1.getId(), productDAOUser1.getId());
		ProductDAO prodRet = productService.findProductById(productDAOUser1.getId());
		CommandDAO commRet = commandService.findCommandById(commandDAO.getId());
		Assert.assertEquals(productDAOUser1.getInitialQuantity(), prodRet.getInitialQuantity(), 0.01);
		Assert.assertEquals(productDAOUser1.getQuantity() - commandDAO.getQuantity(), prodRet.getQuantity(), 0.01);
		Assert.assertEquals(productDAOUser1.getPrice(), prodRet.getPrice(), 0.01);
		Assert.assertEquals(productDAOUser1.getProductType().getName(), prodRet.getProductType().getName());
		Assert.assertEquals(prodRet.getStatus().name(), ProductStatus.UNAVAILABLE.name());

		Assert.assertEquals(commRet.getBuyer().getId(), commandDAO.getBuyer().getId());
		Assert.assertEquals(commRet.getSeller().getId(), commandDAO.getSeller().getId());
		Assert.assertEquals(commRet.getPrice(), commandDAO.getPrice(), 0.01);
		Assert.assertEquals(commRet.getQuantity(), commandDAO.getQuantity(), 0.01);
		for (CommandProductDAO cmdProdDAO : commRet.getCommandProducts()) {
			Assert.assertEquals(cmdProdDAO.getPrice(), commandDAO.getPrice() / 2, 0.01);
			Assert.assertEquals(cmdProdDAO.getQuantity(), commandDAO.getQuantity() / 2, 0.01);
			Assert.assertEquals(cmdProdDAO.getStatus().name(), CommandStatus.CANCELED.name());
			Assert.assertEquals(cmdProdDAO.getProduct().getId(), productDAOUser1.getId());
			Assert.assertNotNull(cmdProdDAO.getCloseDate());
		}
	}

}
