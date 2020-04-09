package com.quentin.sierocki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.controller.model.ProductDTO;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.ProductService;
import com.quentin.sierocki.service.converter.ConvertionException;

@RestController
//@CrossOrigin(origins = "http://localhost:8082")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/user/{idUser}/product")
	public String getProduct(@PathVariable String idUser) {
		return null;
	}

	@PostMapping("/user/{idUser}/product")
	public ProductDTO postProduct(@PathVariable String idUser, @Valid @RequestBody ProductDTO product)
			throws ConvertionException, FunctionnalException {
		return productService.save(idUser, product);
	}

	@PutMapping("/user/{idUser}/product")
	public ProductDTO updateProduct(@PathVariable String idUser, @RequestBody ProductDTO product)
			throws ConvertionException, FunctionnalException {
		return productService.updateProduct(product);
	}
}
