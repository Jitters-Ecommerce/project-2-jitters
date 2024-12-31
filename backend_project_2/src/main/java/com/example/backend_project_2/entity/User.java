package com.example.entity;

import  jakarta.persistence.*;

@Entity
public class User {
    // ATTRIBUTES
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String address;

	// CONSTRUCTORS
	/**
	 * No args constructor for User object.
	 */
	public User () {

	}

	/**
	 * User ID is automatically generated in the database when the user account is registered.
	 */
	public User (String firstName, String lastName, String email, String password, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.address = address;
	}

	// METHODS
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return userId.equals(user.userId) && firstName.equals(user.firstName) && lastName.equals(user.lastName) 
			&& email.equals(user.email) && password.equals(user.password) && address.equals(user.address); 
	}

	@Override
	public String toString() {
		return "User{userId=" + userId +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", address='" + address + '\'' + '}';
	}

}
