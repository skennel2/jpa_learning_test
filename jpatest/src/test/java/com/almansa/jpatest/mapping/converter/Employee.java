package com.almansa.jpatest.mapping.converter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="EmployeeWithEmail")
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	// 컨버터클래스가 autoApply = true로 설정되어있으면 아래 어노테이션은 필요없다.
	@Convert(converter = EmailAddressConverter.class)
	@Column(name = "email_address", length = 100, nullable = true)
	private EmailAddress emailAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

}
