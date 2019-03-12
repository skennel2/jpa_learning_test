package com.almansa.jpatest.mapping.inheritance;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Pen extends Product {
	@Column(name = "color")
	String color;

	public Pen() {
		super();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}