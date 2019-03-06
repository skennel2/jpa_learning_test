package com.almansa.manytoone.twoway;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

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

	public Department getDepartment() {
		return department;
	}

	/**
	 * 사원의 소속부서를 설정해주는 메소드에 기존 부서에서 자신을 제거하고 
	 * 새로운 부서에 자신을 소속시키는 로직을 넣음으로써 양방향 관계설정을 간편하게 해준다.	
	 * 
	 * @param department
	 */
	public void setDepartment(Department department) {
		Employee me = this;
		if (Objects.nonNull(this.department)) {
			if (this.department.getEmployees().contains(me)) {
				this.department.getEmployees().remove(me);
			}
		}

		this.department = department;
		department.getEmployees().add(this);
	}
}
