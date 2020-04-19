package com.quentin.sierocki.legume.back.controller.model;

public class CommandProductDTO {

	private long id;

	private float price;
	private float quantity;
	private long idProduct;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CommandProductDTO [id=" + id + ", price=" + price + ", quantity=" + quantity + ", idProduct="
				+ idProduct + ", status=" + status + "]";
	}

	public void validate() throws ValidationException {
		if (price <= 0)
			throw new ValidationException(this.toString() + " price <=0.");
		if (quantity <= 0)
			throw new ValidationException(this.toString() + " quantity <=0.");
		if (idProduct <= 0)
			throw new ValidationException(this.toString() + " idProduct <=0.");

	}

}
