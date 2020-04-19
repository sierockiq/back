package com.quentin.sierocki.legume.back.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "command")
public class CommandDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private float price;
	private float quantity;
	@Basic
	private LocalDateTime creationDate;

	@Basic
	private LocalDateTime closeDate;

	@Enumerated(EnumType.STRING)
	private CommandStatus status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "command",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	private Set<CommandProductDAO> commandProducts;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserDAO buyer;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserDAO seller;

	public CommandDAO() {
	}

	public CommandDAO(float price, float quantity, LocalDateTime creationDate) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.creationDate = creationDate;
	}

	public CommandDAO(float price, float quantity, LocalDateTime creationDate, LocalDateTime closeDate) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.creationDate = creationDate;
		this.closeDate = closeDate;
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

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
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

	public UserDAO getBuyer() {
		return buyer;
	}

	public void setBuyer(UserDAO buyer) {
		this.buyer = buyer;
	}

	public UserDAO getSeller() {
		return seller;
	}

	public void setSeller(UserDAO seller) {
		this.seller = seller;
	}

	public Set<CommandProductDAO> getCommandProducts() {
		return commandProducts;
	}

	public void setCommandProducts(Set<CommandProductDAO> commandProducts) {
		this.commandProducts = commandProducts;
	}

	@Override
	public String toString() {
		return "CommandDAO [id=" + id + ", price=" + price + ", quantity=" + quantity + ", creationDate=" + creationDate
				+ ", closeDate=" + closeDate + ", status=" + status + ", commandProducts=" + commandProducts.toString()
				+ ", buyer=" + buyer.toString() + ", seller=" + seller.toString() + "]";
	}

	public CommandStatus getStatus() {
		return status;
	}

	public void setStatus(CommandStatus status) {
		this.status = status;
	}

}
