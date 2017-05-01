package com.cutomer.contracts.models;

import java.util.List;

public class Customer {
	private String email;
	private String fullName;
	private long id;
	private List<Contract> contracts;


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}
	
	public List<Contract> getContracts() {
		return this.contracts;
	}

}
