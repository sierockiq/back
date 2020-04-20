package com.quentin.sierocki.legume.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.converter.DAOToDTOConverter;
import com.quentin.sierocki.legume.back.controller.converter.DTOToDAOConverter;
import com.quentin.sierocki.legume.back.controller.model.ProductDTO;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.service.ProductService;
import com.quentin.sierocki.legume.back.service.ServiceException;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/product/save")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProductDTO addNewProduct(@RequestHeader long idUser, @RequestBody ProductDTO productDTO)
			throws ControllerException,ValidationException {
		try {
			productDTO.validate();
			return DAOToDTOConverter.productDAOToProductDTO(
					productService.addNewProduct(idUser, DTOToDAOConverter.productDTOToProductDAO(productDTO)));
		} catch (ServiceException | ConvertionException  e) {
			throw new ControllerException(e.getMessageRetour(),"ProductController->addNewProduct" + e.getPathMethod(), e.getMessage(), e);
		}
	}

	@GetMapping("/product/{idProduct}")
	public void cancelProduct(@RequestHeader long idUser, @PathVariable long idProduct) throws ControllerException {
		try {
			productService.cancelProduct(idUser, idProduct);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessageRetour(),"ProductController->cancelProduct" + e.getPathMethod(), e.getMessage(), e);
		}
	}

}
