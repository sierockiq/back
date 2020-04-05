package com.quentin.sierocki.controller.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommandDTO {

	private int id;
	@Min(0)
	private float price;
	@Max(10000)
	@Min(0)
	private float quantity;
	private Timestamp dateCommande;
	@NotNull
	@NotEmpty
	private String idSeller;

	Set<CommandProductDTO> commandProducts;

	public int getId() {
		return id;
	}

	public CommandDTO() {
	}

	public CommandDTO(int id, float price, float quantity, Timestamp dateCommande, String idSeller) {
		this.id = id;
		this.price = price;
		this.quantity = quantity;
		this.dateCommande = dateCommande;
		this.idSeller = idSeller;
	}

	public void setId(int id) {
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

	public void setQuantite(float quantity) {
		this.quantity = quantity;
	}

	public Timestamp getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Timestamp dateCommande) {
		this.dateCommande = dateCommande;
	}

	public String getIdSeller() {
		return idSeller;
	}

	public void setIdSeller(String idSeller) {
		this.idSeller = idSeller;
	}

	public Set<CommandProductDTO> getCommandProducts() {
		return commandProducts;
	}

	public void setCommandProducts(Set<CommandProductDTO> commandProducts) {
		this.commandProducts = commandProducts;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

}
