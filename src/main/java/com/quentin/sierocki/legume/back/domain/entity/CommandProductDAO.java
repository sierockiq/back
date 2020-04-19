package com.quentin.sierocki.legume.back.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
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
@Table(name = "command_product")
public class CommandProductDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private float price;
	private float quantity;

	@ManyToOne(fetch = FetchType.EAGER)
	private CommandDAO command;

	@OneToOne(fetch = FetchType.EAGER)
	private ProductDAO product;

	@Enumerated(EnumType.STRING)
	private CommandStatus status;

	@Basic
	private LocalDateTime closeDate;

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

	public CommandStatus getStatus() {
		return status;
	}

	public void setStatus(CommandStatus status) {
		this.status = status;
	}

	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDateTime closeDate) {
		this.closeDate = closeDate;
	}

}
