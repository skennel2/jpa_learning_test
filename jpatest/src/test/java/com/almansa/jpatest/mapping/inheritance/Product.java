package com.almansa.jpatest.mapping.inheritance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
// TODO strategy = InheritanceType.SINGLE_TABLE, JOIND, TABLE_PER_CLASS 바꿔 보면서 Table 구조가 어떻게 달라지는지 살펴볼 것
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private Long price;

	protected Product() {
	}

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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

}
