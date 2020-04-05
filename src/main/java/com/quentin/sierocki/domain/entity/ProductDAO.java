package com.quentin.sierocki.domain.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float initialQuantity;
	private float quantity;
	private float price;
	@OneToOne(fetch = FetchType.EAGER)
	private ProductTypeDAO productType;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserDAO farmer;

	@Enumerated(EnumType.STRING)
    private ProductStatus status;
	
	public ProductTypeDAO getProductType() {
		return productType;
	}

	public void setProductType(ProductTypeDAO productType) {
		this.productType = productType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(float initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public UserDAO getFarmer() {
		return farmer;
	}

	public void setFarmer(UserDAO farmer) {
		this.farmer = farmer;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	

}
