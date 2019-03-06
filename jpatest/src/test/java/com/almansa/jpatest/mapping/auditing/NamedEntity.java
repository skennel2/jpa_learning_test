package com.almansa.jpatest.mapping.auditing;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NamedEntity extends EntityBase {

	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
