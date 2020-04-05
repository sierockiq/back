package com.quentin.sierocki.controller.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

public class ProductDTO {

	private int id;

	@Max(10000)
	@Min(0)
	private float initialQuantity;
	@Max(10000)
	@Min(0)
	@Nullable
	private float quantity;
	@Min(0)
	private float price;
	@NotNull @NotEmpty
	private String productTypeName;

	private String status;
	
	public ProductDTO(int id, float initialQuantity, float quantity, float price) {
		super();
		this.id = id;
		this.initialQuantity = initialQuantity;
		this.quantity = quantity;
		this.price = price;
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	


}
