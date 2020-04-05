package com.quentin.sierocki.domain.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String username;
	private String password;
	private String lat;
	private String lng;
	private String adress;
	private String city;
	private String email;
	private String phone;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "farmer")
	private Set<ProductDAO> products;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "seller")
	private Set<CommandDAO> sales;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "buyer")
	private Set<CommandDAO> purchases;

	public Set<ProductDAO> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductDAO> products) {
		this.products = products;
	}

	public Set<CommandDAO> getSales() {
		return sales;
	}

	public void setSales(Set<CommandDAO> sales) {
		this.sales = sales;
	}

	public Set<CommandDAO> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<CommandDAO> purchases) {
		this.purchases = purchases;
	}

	public UserDAO() {
	}

	public UserDAO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}