package com.quentin.sierocki.legume.back.controller.model;

import java.time.LocalDateTime;
import java.util.List;

import com.quentin.sierocki.legume.back.globals.Constants;

public class CommandDTO {

	private long id;
	private float price;
	private float quantity;
	private long idSeller;
	private String status;
	private LocalDateTime creationDate;
	private LocalDateTime closeDate;

	List<CommandProductDTO> commandProducts;

	public CommandDTO() {
	}

	public CommandDTO(long id, float price, float quantity, LocalDateTime creationDate, LocalDateTime closeDate,
			long idSeller) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.creationDate = creationDate;
		this.idSeller = idSeller;
		this.closeDate = closeDate;
	}

	public CommandDTO(long id, float price, float quantity, LocalDateTime creationDate, long idSeller) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.creationDate = creationDate;
		this.idSeller = idSeller;
	}

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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDateTime closeDate) {
		this.closeDate = closeDate;
	}

	public long getIdSeller() {
		return idSeller;
	}

	public void setIdSeller(long idSeller) {
		this.idSeller = idSeller;
	}

	public List<CommandProductDTO> getCommandProducts() {
		return commandProducts;
	}

	public void setCommandProducts(List<CommandProductDTO> commandProducts) {
		this.commandProducts = commandProducts;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CommandDTO [id=" + id + ", price=" + price + ", quantity=" + quantity + ", idSeller=" + idSeller
				+ ", status=" + status + ", creationDate=" + creationDate + ", closeDate=" + closeDate
				+ ", commandProducts=" + commandProducts + "]";
	}

	public void validate() throws ValidationException {
		if (idSeller <= 0)
			throw new ValidationException(Constants.ERROR_VALIDATION_ID_SELLER,this.toString() + " idSeller <=0.");
		if (price <= 0)
			throw new ValidationException(Constants.ERROR_VALIDATION_PRICE,this.toString() + " price <=0.");
		if (quantity <= 0 )
			throw new ValidationException(Constants.ERROR_VALIDATION_QUANTITY,this.toString() + " quantity  <=0.");
		if (commandProducts == null || commandProducts.size() == 0)
			throw new ValidationException(Constants.ERROR_VALIDATION_COMMAND_PRODUCTS,this.toString() + " commandProducts null ou de taille 0.");
		float calcQuantity = 0, calcPrice = 0;
		for (CommandProductDTO commandProductDTO : commandProducts) {
			commandProductDTO.validate();
			calcQuantity = calcQuantity + commandProductDTO.getQuantity();
			calcPrice = calcPrice + commandProductDTO.getPrice();
		}
		if (Math.abs(calcQuantity - this.quantity) > 0.01 || Math.abs(calcPrice - this.price) > 0.01)
			throw new ValidationException(Constants.ERROR_VALIDATION_INCOHERENTE_QUANTITY,this.toString() + " pb de cohérence des données.");
	}

}
