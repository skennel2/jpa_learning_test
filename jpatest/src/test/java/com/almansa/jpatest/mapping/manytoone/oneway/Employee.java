package com.almansa.jpatest.mapping.manytoone.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="EmployeeOw")
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "department_lazy_id")
	private Department departmentLazy;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "department_eager_id")
	private Department departmentEager;

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

	public Department getDepartmentLazy() {
		return departmentLazy;
	}

	public void setDepartmentLazy(Department departmentLazy) {
		this.departmentLazy = departmentLazy;
	}

	public Department getDepartmentEager() {
		return departmentEager;
	}

	public void setDepartmentEager(Department departmentEager) {
		this.departmentEager = departmentEager;
	}

}