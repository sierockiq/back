package com.quentin.sierocki.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CommandProductDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float price;
	private float quantity;

	@ManyToOne(fetch = FetchType.EAGER)
	private CommandDAO command;

	@OneToOne(fetch = FetchType.EAGER)
	private ProductDAO product;

	public int getId() {
		return id;
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

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public CommandDAO getCommand() {
		return command;
	}

	public void setCommand(CommandDAO command) {
		this.command = command;
	}

	public ProductDAO getProduct() {
		return product;
	}

	public void setProduct(ProductDAO product) {
		this.product = product;
	}

}
