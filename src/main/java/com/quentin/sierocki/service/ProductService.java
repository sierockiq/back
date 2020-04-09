package com.quentin.sierocki.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.controller.model.ProductDTO;
import com.quentin.sierocki.domain.entity.ProductDAO;
import com.quentin.sierocki.domain.entity.ProductStatus;
import com.quentin.sierocki.domain.entity.ProductTypeDAO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.domain.repository.ProductRepository;
import com.quentin.sierocki.domain.repository.ProductTypeRepository;
import com.quentin.sierocki.domain.repository.UserRepository;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.converter.ConvertionException;
import com.quentin.sierocki.service.converter.DAOToDTOConverter;
import com.quentin.sierocki.service.converter.DTOToDAOConverter;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductTypeRepository productTypeRepository;

	public ProductDTO save(String idUser, ProductDTO productDTO) throws FunctionnalException, ConvertionException {

		UserDAO userDAO = userRepository.findById(Integer.valueOf(idUser)).orElse(null);

		if (userDAO == null)
			throw new FunctionnalException("User did not exist [id= " + idUser + "]");

		ProductDAO productDAO = DTOToDAOConverter.productDTOToProductDAO(productDTO);

		productDAO.setFarmer(userDAO);
		productDAO.setStatus(ProductStatus.AVAILABLE);

		ProductTypeDAO productTypeDAO = productTypeRepository.findByName(productDTO.getProductTypeName());
		if (productTypeDAO == null) {
			productTypeDAO = productTypeRepository.save(new ProductTypeDAO(productDTO.getProductTypeName()));
		}
		productDAO.setProductType(productTypeDAO);

		ProductDAO productSaved = productRepository.save(productDAO);

		return DAOToDTOConverter.productDAOToProductDTO(productSaved);

	}

	public ProductDTO updateProduct(ProductDTO productDTO) throws ConvertionException, FunctionnalException {
		ProductDAO productDAO = productRepository.findById(productDTO.getId()).orElse(null);
		if (productDAO == null)
			throw new FunctionnalException("productdidn't exist [id= " + productDTO.getId() + "]");

		if (productDTO.getStatus() != null && !productDTO.getStatus().isEmpty()) {
			productDAO.setStatus(ProductStatus.valueOf(productDTO.getStatus()));
		}
		ProductDAO productSaved = productRepository.save(productDAO);

		return DAOToDTOConverter.productDAOToProductDTO(productSaved);

	}

}
