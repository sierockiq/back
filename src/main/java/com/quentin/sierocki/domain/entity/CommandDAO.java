package com.quentin.sierocki.domain.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "command")
public class CommandDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float price;
	private float quantity;
	private Timestamp dateCommande;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "command")
	private Set<CommandProductDAO> commandProducts;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserDAO buyer;

	@ManyToOne(fetch = FetchType.EAGER)
	private UserDAO seller;

	@Enumerated(EnumType.STRING)
    private CommandStatus status;
	
	public CommandDAO() {
	}

	public CommandDAO(float price, float quantity, Timestamp dateCommande) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.dateCommande = dateCommande;
	}


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

	public Timestamp getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Timestamp dateCommande) {
		this.dateCommande = dateCommande;
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

	public CommandStatus getStatus() {
		return status;
	}

	public void setStatus(CommandStatus status) {
		this.status = status;
	}

	public Set<CommandProductDAO> getCommandProducts() {
		return commandProducts;
	}

	public void setCommandProducts(Set<CommandProductDAO> commandProducts) {
		this.commandProducts = commandProducts;
	}

	

	
	

}
