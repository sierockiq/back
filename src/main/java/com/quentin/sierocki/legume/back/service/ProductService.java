package com.quentin.sierocki.legume.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.legume.back.domain.entity.CommandStatus;
import com.quentin.sierocki.legume.back.domain.entity.ProductDAO;
import com.quentin.sierocki.legume.back.domain.entity.ProductStatus;
import com.quentin.sierocki.legume.back.domain.entity.ProductTypeDAO;
import com.quentin.sierocki.legume.back.domain.entity.UserDAO;
import com.quentin.sierocki.legume.back.domain.repository.ProductRepository;
import com.quentin.sierocki.legume.back.domain.repository.ProductTypeRepository;
import com.quentin.sierocki.legume.back.globals.Constants;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private CommandService commandService;

	@Autowired
	private ProductTypeRepository productTypeRepository;

	/**
	 * @param idUser
	 * @return
	 * @throws ServiceException
	 */
	/**
	 * @param idUser
	 * @return
	 * @throws ServiceException
	 */
	public ProductDAO findProductById(long idProduct) throws ServiceException {
		try {
			ProductDAO productDAO = productRepository.findById(idProduct).orElse(null);
			if (productDAO == null)
				throw new ServiceException("Le produit n'existe pas avec l'id " + idProduct);

			return productDAO;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->findProductById - ", e.getMessage(),
					e);
		}
	}

	/**
	 * Ajout d'un nouveau produit + création d'un nouveau type de produit au besoin
	 * 
	 * @param idUser
	 * @param productDTO
	 * @return
	 * @throws ServiceException
	 */
	public ProductDAO addNewProduct(long idUser, ProductDAO productDAO) throws ServiceException {
		try {
			UserDAO userDAO = userService.findUserById(idUser);
			productDAO.setFarmer(userDAO);
			productDAO.setStatus(ProductStatus.AVAILABLE);
			ProductTypeDAO productTypeDAO = productTypeRepository.findByName(productDAO.getProductType().getName());
			if (productTypeDAO == null) {
				productTypeDAO = productTypeRepository.save(productDAO.getProductType());
			}
			productDAO.setProductType(productTypeDAO);
			ProductDAO productSaved = productRepository.save(productDAO);
			return productSaved;
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->save - ", e.getMessage(), e);
		}
	}

	/**
	 * Passe le status d'un produit à UNAVAILABLE et celui de toutes ses
	 * commandProducts associées à CANCELED
	 * 
	 * @param idProduct
	 * @param idProduct2
	 * @throws ServiceException
	 */
	public void cancelProduct(long idUser, long idProduct) throws ServiceException {
		try {
			ProductDAO productDAO = findProductById(idProduct);
			if (idUser != productDAO.getFarmer().getId())
				throw new ServiceException(
						"L'id du vendeur du produit n'est pas le même que celui qui fait la demande.");
			commandService.changeStatusByProductId(idProduct, CommandStatus.CANCELED);
			productDAO.setStatus(ProductStatus.UNAVAILABLE);
			productRepository.save(productDAO);
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->deleteProduct - ", e.getMessage(), e);
		}
	}

	public void updateProductQuantityWhenUserMakeCommand(long idProduct, float quantity, long idSeller)
			throws ServiceException {
		try {
			ProductDAO productDAO = findProductById(idProduct);
			if (idSeller != productDAO.getFarmer().getId())
				throw new ServiceException("L'id du vendeur du produit" + productDAO.getFarmer().getId()
						+ " et l'id vendeur " + idSeller + "sont différent : ");
			if (quantity > productDAO.getQuantity())
				throw new ServiceException("La quantité commandée est trop importante.");

			productDAO.setQuantity(productDAO.getQuantity() - quantity);
			productRepository.save(productDAO);
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->updateProductQuantity - ",
					e.getMessage(), e);
		}
	}

	public void updateProductQuantityWhenCommandIsCancelled(long idProduct, float quantity) throws ServiceException {
		try {
			ProductDAO productDAO = findProductById(idProduct);

			if (productDAO.getInitialQuantity() < productDAO.getQuantity() + quantity)
				throw new ServiceException(
						"La quantité d'un produit ne doit pas être supérieure à la quantité initiale");

			productDAO.setQuantity(productDAO.getQuantity() + quantity);
			productRepository.save(productDAO);
		} catch (Exception e) {
			throw new ServiceException(Constants.ERROR_SERVICE, "ProductService->updateProductQuantity - ",
					e.getMessage(), e);
		}
	}

}
