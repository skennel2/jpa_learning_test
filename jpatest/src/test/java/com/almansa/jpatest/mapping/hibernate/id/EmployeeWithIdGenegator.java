package com.almansa.jpatest.mapping.hibernate.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class EmployeeWithIdGenegator {
	@Id
	@GeneratedValue(generator = "id_generator")
	@GenericGenerator(name = "id_generator", 
		strategy = "com.almansa.jpatest.mapping.hibernate.id.UUIDIdentifierGenerator", 
		parameters = @Parameter(name = "prefix", value = "pk_"))
	private String id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
