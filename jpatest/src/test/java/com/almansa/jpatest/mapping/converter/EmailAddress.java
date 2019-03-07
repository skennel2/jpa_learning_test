package com.almansa.jpatest.mapping.converter;

public class EmailAddress {
	private String emailAddress;

	public EmailAddress(String emailAddress) {
		super();
		this.emailAddress = emailAddress;
	}

	public String getEmailStringValue() {
		return emailAddress;
	}
}
