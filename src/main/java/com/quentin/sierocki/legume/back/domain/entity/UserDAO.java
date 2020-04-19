package com.quentin.sierocki.legume.back.domain.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user")
public class UserDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private float lat;
	private float lng;
	@NotBlank
	private String adress;
	@NotBlank
	private String city;
	@NotBlank
	@Column(unique = true)
	@Email
	private String email;
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phone;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
	private Set<ProductDAO> products;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
	private Set<CommandDAO> sales;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	


}