package com.almansa.manytoone.twoway;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	// mappedBy는 연관관계의 주인 엔티티의 매핑되는'필드'명이 들어와야 한다.
	// 연관관계 주인 엔티티의 의미는 데이터 베이스 테이블상에서 FK 필드를 가지고 있는 테이블의 엔티티를 말한다. 
	@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	private List<Employee> employees;

	public Department() {
		super();
		this.employees = new ArrayList<>();
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

	protected List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		if(Objects.isNull(employees)) {
			throw new NullPointerException("employees");
		}
		this.employees = employees;
	}

}
