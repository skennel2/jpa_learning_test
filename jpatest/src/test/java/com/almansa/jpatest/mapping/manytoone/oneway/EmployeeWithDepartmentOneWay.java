package com.almansa.jpatest.mapping.manytoone.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EmployeeWithDepartmentOneWay {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "department_lazy_id")
	private DepartmentOneWay departmentLazy;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "department_eager_id")
	private DepartmentOneWay departmentEager;

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

	public DepartmentOneWay getDepartmentLazy() {
		return departmentLazy;
	}

	public void setDepartmentLazy(DepartmentOneWay departmentLazy) {
		this.departmentLazy = departmentLazy;
	}

	public DepartmentOneWay getDepartmentEager() {
		return departmentEager;
	}

	public void setDepartmentEager(DepartmentOneWay departmentEager) {
		this.departmentEager = departmentEager;
	}

}