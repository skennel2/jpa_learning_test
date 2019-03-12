package com.almansa.jpatest.mapping.inheritance;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Book extends Product {

	@Column(name = "author")
	private String author;

	public Book() {
		super();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
