package com.quentin.sierocki.legume.back.controller.model;

import com.quentin.sierocki.legume.back.globals.Constants;

public class ProductDTO {

	private long id;
	private float initialQuantity;
	private float quantity;
	private float price;
	private String productTypeName;
	private String status;

	public ProductDTO(long id, float initialQuantity, float quantity, float price) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", initialQuantity=" + initialQuantity + ", quantity=" + quantity + ", price="
				+ price + ", productTypeName=" + productTypeName + ", status=" + status + "]";
	}

	public void validate() throws ValidationException {
		if (initialQuantity <= 0)
			throw new ValidationException(Constants.ERROR_VALIDATION_QUANTITY,this.toString() + "initialQuantity <=0");
		if (price <= 0)
			throw new ValidationException(Constants.ERROR_VALIDATION_PRICE,this.toString() + "price <=0");
		if (productTypeName == null || productTypeName.isEmpty())
			throw new ValidationException(Constants.ERROR_VALIDATION_PRODUCT_TYPE_NAME,this.toString() + "productTypeName null ou empty");
		if (initialQuantity < quantity)
			throw new ValidationException(Constants.ERROR_VALIDATION_QUANTITY_SUP_INITIAL_QUANTITY,this.toString() + "initialQuantity<quantity");

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Float.floatToIntBits(initialQuantity);
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((productTypeName == null) ? 0 : productTypeName.hashCode());
		result = prime * result + Float.floatToIntBits(quantity);
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(initialQuantity) != Float.floatToIntBits(other.initialQuantity))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (productTypeName == null) {
			if (other.productTypeName != null)
				return false;
		} else if (!productTypeName.equals(other.productTypeName))
			return false;
		if (Float.floatToIntBits(quantity) != Float.floatToIntBits(other.quantity))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
