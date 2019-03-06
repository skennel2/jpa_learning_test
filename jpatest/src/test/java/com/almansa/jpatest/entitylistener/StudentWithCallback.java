package com.almansa.jpatest.entitylistener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@Entity
public class StudentWithCallback {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "first_name", length = 10, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 10, nullable = false)
	private String lastName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFullName() {
		return firstName + lastName;
	}

	@PrePersist
	public void prePersist() {
		// before a new entity is persisted (added to the EntityManager).
		System.out.println("prePersist");
	}

	@PreRemove
	public void preRemove() {
		// when an entity is marked for removal in the EntityManager.
		System.out.println("preRemove");
	}

	@PreUpdate
	public void preUpdate() {
		// when an entity is identified as modified by the EntityManager.
		System.out.println("preUpdate");
	}

	@PostPersist
	public void postPersist() {
		// after storing a new entity in the database (during commit or flush).
		System.out.println("postPersist");
	}

	@PostRemove
	public void postRemove() {
		// after deleting an entity from the database (during commit or flush).
		System.out.println("postRemove");
	}

	@PostUpdate
	public void postUpdate() {
		// after updating an entity in the database (during commit or flush).
		System.out.println("postUpdate");
	}

	@PostLoad
	public void postLoad() {
		// after an entity has been retrieved from the database.
		System.out.println("postLoad");
	}
}
