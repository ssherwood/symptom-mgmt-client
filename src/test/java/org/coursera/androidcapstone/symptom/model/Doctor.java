package org.coursera.androidcapstone.symptom.model;

import org.coursera.androidcapstone.symptom.sdr.SDREmbeddedLinks;

/**
 * 
 */
public class Doctor extends SDREmbeddedLinks {

	private String username;
	private Integer nationalProviderNumber;
	private String firstName;
	private String lastName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNationalProviderNumber() {
		return nationalProviderNumber;
	}

	public void setNationalProviderNumber(Integer nationalProviderNumber) {
		this.nationalProviderNumber = nationalProviderNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
