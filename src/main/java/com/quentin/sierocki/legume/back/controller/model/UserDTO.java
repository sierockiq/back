package com.quentin.sierocki.legume.back.controller.model;

import java.util.List;
import java.util.regex.Pattern;

import com.quentin.sierocki.legume.back.globals.Constants;

public class UserDTO {
	private long id;
	private String username;
	private String password;
	private String token;
	private float lat;
	private float lng;
	private String adress;
	private String city;
	private String email;
	private String phone;
	private List<ProductDTO> products;
	private List<CommandDTO> sales;
	private List<CommandDTO> purchases;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public List<CommandDTO> getSales() {
		return sales;
	}

	public void setSales(List<CommandDTO> sales) {
		this.sales = sales;
	}

	public List<CommandDTO> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<CommandDTO> purchases) {
		this.purchases = purchases;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", lat=" + lat + ", lng="
				+ lng + ", adress=" + adress + ", city=" + city + ", email=" + email + ", phone=" + phone
				+ ", products=" + products + ", sales=" + sales + ", purchases=" + purchases + "]";
	}

	public void validate() throws ValidationException {
		if(username == null || username.isEmpty())
			throw new ValidationException(this.toString() + " username null ou empty");
		if(password == null || password.isEmpty())
			throw new ValidationException(this.toString() + " password null ou empty");
		if (    adress == null|| adress.isEmpty() )
			throw new ValidationException(this.toString() + " adress null ou empty");
		if (    city == null|| city.isEmpty() )
			throw new ValidationException(this.toString() +" city null ou empty");
		if (     email == null || email.isEmpty() || !Pattern.compile(Constants.REGEXP_EMAIL).matcher(email).matches())
			throw new ValidationException(this.toString() +" email null ou empty ou incorrect");
		if(phone == null || phone.length()!=10 || phone.isEmpty()  ) 
			throw new ValidationException(this.toString() + " phone null ou empty ou pas de 10 chiffres");
		if(Math.abs(lat) <0.01 && Math.abs(lng) < 0.01)
			throw new ValidationException(this.toString() + " lat et lng sont à 0");
	}

}
