package com.almansa.jpatest.mapping.hibernate.dynamic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

@Entity
/*
 * 엔티티를 조회할때의 Where구문을 추가할 수있다. Soft Delete를 구현할 때 유용해보인다.
 * DB값 기준임에 유의 
 */
@Where(clause = "is_deleted = false") 
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "gross_income")
	private long grossIncome;

	@Column(name = "tax_in_per")
	private int taxInPercents;
	
	/*
	 *  데이터 베이스에서 값을 가져오는 시점에 어노테이션 표현식에 따라 필드값을 가져온다.
	 *  DB값 기준임에 유의. DB종속적인 함수등의 사용에 제한이 없다. 
	 *  readonly field에 어울린다.
	 *  DB에 실제 컬럼이 생기는 것은 아니다.
	 *  
	 */
	@Formula("gross_income * tax_in_per / 100")
	private long tax;
	
	@Column(name = "is_deleted", nullable = true)
	private boolean isDeleted;

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

	public long getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(long grossIncome) {
		this.grossIncome = grossIncome;
	}

	public int getTaxInPercents() {
		return taxInPercents;
	}

	public void setTaxInPercents(int taxInPercents) {
		this.taxInPercents = taxInPercents;
	}

	public long getTax() {
		return tax;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
