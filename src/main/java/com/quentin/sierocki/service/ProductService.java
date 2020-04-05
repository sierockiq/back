package com.quentin.sierocki.service;

import java.util.Optional;

import javax.validation.Valid;

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
	

	public ProductDTO save(String idUser, ProductDTO productDTO) {
		Optional<UserDAO> userExist = userRepository.findById(Integer.valueOf(idUser));
		if (userExist.isPresent()) {
			ProductDAO productDAO = DTOToDAOConverter.productDTOToProductDAO(productDTO);
			productDAO.setFarmer(userExist.get());
			productDAO.setStatus(ProductStatus.AVAILABLE);
			ProductTypeDAO productTypeDAO = productTypeRepository.findByName(productDTO.getProductTypeName());
			if(productTypeDAO==null) {
				productTypeDAO = productTypeRepository.save(new ProductTypeDAO(productDTO.getProductTypeName()));
			}
			productDAO.setProductType(productTypeDAO);
			ProductDAO productSaved = productRepository.save(productDAO);
			return DAOToDTOConverter.productDAOToProductDTO(productSaved);
		} else {
			return null;
		}
	}

	public ProductDTO updateProduct(ProductDTO productDTO) {
		Optional<ProductDAO> productExist = productRepository.findById(productDTO.getId());
		if(productExist.isPresent()) {
			ProductDAO productDAO = productExist.get();
			if(productDTO.getStatus()!=null && !productDTO.getStatus().isEmpty()) {
				productDAO.setStatus(ProductStatus.valueOf(productDTO.getStatus()));
			}
			ProductDAO productSaved = productRepository.save(productDAO);
			return DAOToDTOConverter.productDAOToProductDTO(productSaved);
		}else {
			return null;
		}
	}

}
