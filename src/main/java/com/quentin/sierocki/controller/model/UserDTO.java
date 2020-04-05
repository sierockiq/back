package com.quentin.sierocki.controller.model;

import java.util.Set;

public class UserDTO {
	private int id;
	private String username;
	private String password;
	private String lat;
	private String lng;
	private String adress;
	private String city;
	private String email;
	private String phone;
	private Set<ProductDTO> products;
	private Set<CommandDTO> sales;
	private Set<CommandDTO> purchases;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<CommandDTO> getSales() {
		return sales;
	}

	public void setSales(Set<CommandDTO> sales) {
		this.sales = sales;
	}

	public Set<CommandDTO> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<CommandDTO> purchases) {
		this.purchases = purchases;
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

	public Set<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductDTO> products) {
		this.products = products;
	}

}
